library(purrr)
library(fs)
install.packages("pkgcond", repos = "http://cran.us.r-project.org")
quiet <- pkgcond::suppress_conditions

download_dir <- "download"
data_dir <- "data"
fs::dir_create(data_dir)
files <- fs::dir_ls(download_dir) |>
    map(~ {
        df <- vroom::vroom(.x) |> quiet()
        df
    }) |>
    dplyr::bind_rows() |>
    arrow::write_parquet(sink = file.path(data_dir, "hgnc_complete_set.parquet"))
