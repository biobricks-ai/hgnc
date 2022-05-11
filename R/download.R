library(fs)
library(purrr)
library(rvest)
options(timeout=1800)

download_dir <- "download"
fs::dir_create(download_dir)

base_name <- function(filename) {
  file_path_sans_ext(basename(filename))
}

print("Downloading Files")
url='http://ftp.ebi.ac.uk/pub/databases/genenames/hgnc/archive/monthly/tsv'
ftp=stringr::str_replace(url,"http","ftp")
url |> rvest::read_html() |> 
rvest::html_elements("a") |> 
rvest::html_attr("href") |>
purrr::keep(~ grepl("complete_set",.x)) |>
walk(~ download.file(url=file.path(ftp,.x),destfile=(file.path(download_dir,.x))))