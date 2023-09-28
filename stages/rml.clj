(ns rml
  (:require [clojure.string :as str]
            [insilica.ontology.biochem :as bc]
            [insilica.ontology.core :as ont :refer [clazz def-classes]]
            [tech.v3.dataset :as ds]
            [tmducken.duckdb :as duckdb]))

(def classes
  [(clazz
    "HGNCDateApprovedReserved"
    nil
    "http://example.com/HGNC/DateApprovedReserved"
    :property-type ont/XMLDate)
   (clazz
    "HGNCDateModified"
    nil
    "http://example.com/HGNC/DateModified"
    :property-type ont/XMLDate)
   (clazz
    "HGNCLocusGroup"
    nil
    "http://example.com/HGNC/LocusGroup"
    :property-type ont/XMLString)
   (clazz
    "HGNCLocusType"
    nil
    "http://example.com/HGNC/LocusType"
    :property-type ont/XMLString)
   (clazz
    "HGNCManeSelect"
    nil
    "http://example.com/HGNC/ManeSelect"
    :property-type ont/XMLString)
   (clazz
    "HGNCRecord"
    nil
    "http://example.com/HGNC/Record"
    :resource-prefix "http://example.com/HGNC/Record/")
   (clazz
    "HGNCStatus"
    nil
    "http://example.com/HGNC/Status"
    :property-type ont/XMLString)])

(def-classes classes)

(defn get-hgnc [conn]
  (duckdb/sql->dataset conn "select * from 'brick/hgnc_complete_set.parquet'"))

(defn split-values [has-fn re]
  (fn [subj resource-id-or-property]
    (->> (str/split resource-id-or-property re)
         (mapcat (partial has-fn subj)))))

(defn split-pipes [has-fn]
  (split-values has-fn #"\|"))

(def hgnc-mapping
  {(split-pipes bc/hasCCDSID) "ccds_id"
   bc/hasEnsemblID "ensembl_gene_id"
   bc/hasEntrezID "entrez_id"
   bc/hasGeneGroup "gene_group"
   bc/hasGeneLocation "location"
   bc/hasGeneName "name"
   bc/hasHGNCID "hgnc_id"
   bc/hasMeropsID "merops"
   bc/hasMGDMGI "mgd_id"
   bc/hasOmimID "omim_id"
   (split-pipes bc/hasPubmedID) "pubmed_id"
   bc/hasRefSeqAccession "refseq_accession"
   (split-pipes bc/hasRGDID) "rgd_id"
   bc/hasSymbol "symbol"
   bc/hasUCSCID "ucsc_id"
   bc/hasVegaID "vega_id"
   hasHGNCDateApprovedReserved #(some-> (get % "date_approved_reserved") str)
   hasHGNCDateModified #(some-> (get % "date_modified") str)
   hasHGNCLocusGroup "locus_group"
   hasHGNCLocusType "locus_type"
   hasHGNCManeSelect "mane_select"
   hasHGNCStatus "status"})

(defn hgnc-row->triples
  [{:as data :strs [hgnc_id uniprot_ids]}]
  (let [subj (ont/resource-iri HGNCRecord hgnc_id)]
    (concat
     [(ont/isA subj HGNCRecord)]
     (some-> uniprot_ids
             (str/split #"\|")
             (->> (mapcat #(bc/hasUniprotID subj %))))
     (ont/subj-mappings subj data hgnc-mapping))))

(defn -main [filename]
  (duckdb/initialize!)
  (let [db (duckdb/open-db)
        conn (duckdb/connect db)]
    (try
      (->> (mapcat hgnc-row->triples (ds/rows (get-hgnc conn)))
           (ont/write-nt filename))
      (finally
        (duckdb/close-db db)))))

(comment
  (do
    (def filename "rdf/hgnc.nt")
    (duckdb/initialize!)
    (def db (duckdb/open-db))
    (def conn (duckdb/connect db))
    (def hgnc (get-hgnc conn)))

  (->> hgnc ds/rows first (into (sorted-map)))
  (->> hgnc ds/rows first hgnc-row->triples ont/nt-seq)

  ;; Discover that uniprot ids are separated with | characters
  (->> hgnc ds/rows (map #(get % "uniprot_ids")) (filter #(< 10 (count %))))

  ;; Test unitprot id triples
  (->> (hgnc-row->triples {"hgnc_id" "HGNC:0" "uniprot_ids" "P0DP91|Q03468"})
       ont/nt-seq)
  )
