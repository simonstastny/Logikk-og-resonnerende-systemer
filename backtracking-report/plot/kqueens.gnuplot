set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 680, 400
set style line 1 lt 1 lw 1 pt 3 lc rgb "red"
set style line 2 lt 1 lw 1 pt 3 lc rgb "blue"
set output 'queens.png'
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set ytics  norangelimit
set ytics   (10000, 20000, 30000, 40000, 50000, 60000, 70000)
set title "K-Queens puzzle: K versus run-time" 
set xrange [ 0.0000 : 120.000 ] noreverse nowriteback
set yrange [ 0.0000 : 70000 ] noreverse nowriteback
set lmargin  9
set rmargin  2
set style func linespoints
set ylabel "Y-axis" textcolor lt 1
set xlabel "Queens count"
plot  'data.dat' with linespoint title "smallest domain choice" ls 1, 'data2.dat' with linespoint title "first unassigned choice" ls 2