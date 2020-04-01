find . -name '*.java' -or -name '*.xml' | xargs wc -l | grep 'total'  | awk '{ SUM += $1; print $1} END { print "Total text lines in PHP and JS",SUM }'
