library(fs)
library(arrow)

download_dir <- "download"
data_dir <- "data"
fs::dir_create(data_dir)
arrow::write_parquet(vroom::vroom(fs::dir_ls(download_dir)[[1]]),
    sink = file.path(data_dir, "hgnc_complete_set.parquet"))