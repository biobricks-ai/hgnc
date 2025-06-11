# HGNC (Human Gene Nomenclature Committee) — Complete Gene Set

> **Brick ID:** `biobricks-ai/hgnc`
>
> **Snapshot date:** *latest monthly release automatically fetched at build time*
> **Rows:** ~43 718 genes  |  **Columns:** 54
> **File size (Parquet):** ≈ 9 MB
> **Upstream licence:** Creative Commons 0 (CC‑0)

---

## What is this brick?

This brick provides the full, authoritative list of human gene symbols and metadata curated by the **HGNC**.  The table maps every approved gene symbol (e.g. `TP53`) to its stable HGNC ID (`HGNC:11998`) and a rich set of cross‑references (Entrez, Ensembl, MANE, UniProt, OMIM …).

The data are stored as an **Apache Parquet** file so that you can query or join the 44 k genes instantly from Python, R, Spark or DuckDB without any extra wrangling.

---

## Provenance & update policy

* **Source URL:** [https://www.genenames.org/download/statistics-and-files/](https://www.genenames.org/download/statistics-and-files/)
* **Upstream file:** `hgnc_complete_set_YYYY‑MM‑DD.txt` (tab‑separated); HGNC publish a fresh archive on the first business day each month.
* **Build script:** pulls the newest file from the EBI FTP mirror, reads it with Arrow/Vroom, and writes it **loss‑lessly** to `hgnc_complete_set.parquet`.  No columns or values are modified.
* **Versioning:** every monthly snapshot receives its own Git commit + content hash; you can reproducibly pin any past release via BioBricks’ `brick_pull("hgnc@<commit>")` semantics.

---

## File layout

```
brick/
└─ hgnc_complete_set.parquet   # one row per HGNC record
```

### Selected columns (full list = 54)

| Column            | Example                          | Description                                     |                                            |
| ----------------- | -------------------------------- | ----------------------------------------------- | ------------------------------------------ |
| `hgnc_id`         | `HGNC:5`                         | Stable HGNC primary key                         |                                            |
| `symbol`, `name`  | `A1BG`, “alpha‑1‑B glycoprotein” | Approved gene symbol and full name              |                                            |
| `locus_group`     | `protein‑coding gene`            | Broad locus category                            |                                            |
| `status`          | `Approved`                       | Record status (`Approved`, `Entry Withdrawn` …) |                                            |
| `location`        | `19q13.43`                       | Cytogenetic band                                |                                            |

*A full column dictionary is available from HGNC’s “Download‑files help” page and applies 1‑to‑1 here.*

---

## Quick start

### Python

```python
import biobricks as bb
import pandas as pd
hgnc = biobricks.assets("hgnc")
path = hgnc.hgnc_complete_set_parquet  # absolute file path

genes = pd.read_parquet(path)
print(genes.head())
```

---

## Frequently asked questions

**Q.** Why not keep the TSV?
**A.** Parquet is columnar, compressed and splittable → 10× faster scans and \~3× smaller on disk.

**Q.** Are multi‑valued fields exploded into arrays?
**A.** No. We preserve HGNC’s pipe/comma‑delimited strings verbatim so the file stays byte‑reversible to the source.

**Q.** How often is the brick rebuilt?
**A.** A GitHub Action runs on the 1st of each month, checks HGNC for a new archive, and pushes a new commit if the upstream hash changed.

---

## Citation

If you use this brick, please cite:

```
HGNC. HUGO Gene Nomenclature Committee Database (2025 release).
Available at https://www.genenames.org/ (accessed <YYYY‑MM‑DD>).
BioBricks.ai – hgnc brick, commit <commit‑hash>.
```

---

## Licence

The HGNC dataset is released under **CC‑0**.  This brick inherits that licence; feel free to use, modify or redistribute without restriction.  Credit to HGNC is appreciated.
