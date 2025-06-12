
---

### **hgnc/README.md**

```markdown
# 📦 BioBricks.ai / HGNC   <!-- built 2025-06-12 -->

_The HGNC (HUGO Gene Nomenclature Committee) maintains the official list
of approved human gene symbols.  
This brick snapshots the registry (April 2024) so every pipeline resolves
a stable symbol → ID mapping, independent of future HGNC updates._
hgnc_complete_set.parquet	6.3MB
---

## ➤ Quick start

```python
import biobricks as bb, pandas as pd

hgnc  = bb.assets("hgnc")
genes = pd.read_parquet(hgnc.hgnc_complete_set_parquet)

genes.query("symbol == 'TP53'")[["hgnc_id", "name", "location"]]
