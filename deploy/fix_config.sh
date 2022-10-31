#!/bin/bash
# put CI envoirment into ant config
printf "ant-support.prefix.dir=$0/ecom\n" >> deploy/config.properties
printf "libdir=$0/MedosLibs\n" >> deploy/config.properties
cat deploy/config.properties
cp deploy/config.properties ecom/ant-support/