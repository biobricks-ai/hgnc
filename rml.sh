#!/usr/bin/env bash

python -m morph_kgc morph-kgc.ini
mkdir -p rdf
rdf2hdt rdf/hgnc_complete_set.nt rdf/hgnc_complete_set.hdt
rm -f rdf/hgnc_complete_set.nt
