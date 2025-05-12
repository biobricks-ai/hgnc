# hgnc

## 🔍 Overview
TODO: one‑sentence plain‑language description.

## 📦 Data Source

- **TODO: data source name**  
  URL: [TODO: https://example.com](TODO: https://example.com)
  <br>Citation: TODO: Author et al. (YEAR)
  <br>License: TODO: license


## 🔄 Transformations
TODO: describe any processing, or say 'none — preserved as‑is'

## 📁 Assets

- `TODO.parquet` (Parquet): TODO: what this file contains


## 🧪 Usage
```bash
biobricks install hgnc

import biobricks as bb
import pandas as pd

paths = bb.assets("hgnc")

# Available assets:

df_1 = pd.read_parquet(paths.TODO_parquet)


print(df_1.head())      # Preview the first asset

## Additional Information
# hgnc

## 🔍 Overview
TODO: one‑sentence plain‑language description.

## 📦 Data Source

- **TODO: data source name**  
  URL: [TODO: https://example.com](TODO: https://example.com)
  <br>Citation: TODO: Author et al. (YEAR)
  <br>License: TODO: license


## 🔄 Transformations
TODO: describe any processing, or say 'none — preserved as‑is'

## 📁 Assets

- `TODO.parquet` (Parquet): TODO: what this file contains


## 🧪 Usage
```bash
biobricks install hgnc

import biobricks as bb
import pandas as pd

paths = bb.assets("hgnc")

# Available assets:

df_1 = pd.read_parquet(paths.TODO_parquet)


print(df_1.head())      # Preview the first asset

## Additional Information
# hgnc

## 🔍 Overview
TODO: one‑sentence plain‑language description.

## 📦 Data Source

- **TODO: data source name**  
  URL: [TODO: https://example.com](TODO: https://example.com)
  <br>Citation: TODO: Author et al. (YEAR)
  <br>License: TODO: license


## 🔄 Transformations
TODO: describe any processing, or say 'none — preserved as‑is'

## 📁 Assets

- `TODO.parquet` (Parquet): TODO: what this file contains


## 🧪 Usage
```bash
biobricks install hgnc

import biobricks as bb
import pandas as pd

paths = bb.assets("hgnc")

# Available assets:

df_1 = pd.read_parquet(paths.TODO_parquet)


print(df_1.head())      # Preview the first asset

## Additional Information
# HGNC

<a href="https://github.com/biobricks-ai/HGNC/actions"><img src="https://github.com/biobricks-ai/HGNC/actions/workflows/bricktools-check.yaml/badge.svg?branch=master"/></a>

## Description
> The HGNC is a resource for approved human gene nomenclature containing ~42000 gene symbols and names and 1300+ gene families and sets

## Usage
```{R}
biobricks::install_brick("HGNC")
biobricks::brick_pull("HGNC")
biobricks::brick_load("HGNC")
```
