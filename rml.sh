#!/usr/bin/env bash

set -eux

mkdir -p rdf
clj -M:rdf rdf/hgnc_complete_set.nt
rdf2hdt rdf/hgnc_complete_set.nt rdf/hgnc_complete_set.hdt
rm -f rdf/hgnc_complete_set.nt
