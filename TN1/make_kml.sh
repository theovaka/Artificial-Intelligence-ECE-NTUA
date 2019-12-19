#!/bin/bash
taxis=$( ls $1 | grep .out)
colors=("FF0014", "FF7814", "FFF014", "3CFF78", "B4FF78", "0078FF", "7878FF", "B478FF", "14FF00", "14C8F0", "A0F014")
j=0

# Print the basic text of the kml file
echo '<?xml version="1.0" encoding="UTF-8"?>' > $2
echo '<kml xmlns="http://earth.google.com/kml/2.1">' >> $2
echo '<Document>' >> $2
echo '<name>Taxi Routes</name>' >> $2
echo '<Style id="green">' >> $2
echo '<LineStyle>' >> $2
echo '<color>ff009900</color>' >> $2
echo '<width>4</width>' >> $2
echo '</LineStyle>' >> $2
echo '</Style>' >> $2
echo '<Style id="red">' >> $2
echo '<LineStyle>' >> $2
echo '<color>ff0000ff</color>' >> $2
echo '<width>4</width>' >> $2
echo '</LineStyle>' >> $2
echo '</Style>' >> $2

for i in $taxis; do
	echo '<Placemark>' >> $2
	echo '<name>Taxi '$i'</name>' >> $2
	echo '<styleUrl>#line-'${colors[j]}'-nodesc</styleUrl>' >> $2
	echo '<LineString>' >> $2
	echo '<altitudeMode>relative</altitudeMode>' >> $2
	echo '<coordinates>' >> $2

	var=$(cat $1$i)
	echo "$var" >> $2

	echo '</coordinates>' >> $2
	echo '</LineString>' >> $2
	echo '</Placemark>' >> $2

	let j+=1
done

#Print the finish commands
echo '</Document>' >> $2
echo '</kml>' >> $2
