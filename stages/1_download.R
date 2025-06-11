library(fs)
library(purrr)
library(rvest)
options(timeout=1800)

download_dir <- "download"
fs::dir_create(download_dir)

print("Downloading Files")
url='https://storage.googleapis.com/public-download-files/hgnc/tsv/tsv/hgnc_complete_set.txt'
ftp = stringr::str_replace(url,"http","ftp")
url |> rvest::read_html() |> 
rvest::html_elements("a") |> 
rvest::html_attr("href") |>
purrr::keep(~ grepl("complete_set",.x)) |>
sort(decreasing=TRUE) |>
purrr::pluck(1) |>
purrr::walk(~ download.file(url=file.path(ftp,.x),destfile=(file.path(download_dir,.x))))
