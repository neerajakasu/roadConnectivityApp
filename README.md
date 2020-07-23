### Road Connectivity Application

This application determines if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.   

List of roads is available in a city.txt file. The file contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.'

## Endpoint

[http://localhost:8080/connected?origin=city1&destination=city2](http://localhost:8080/connected?origin=city1&destination=city2)

### Examples

`Boston` and `Newark` are connected:

[http://localhost:8080/connected?origin=Boston&destination=Newark](http://localhost:8080/connected?origin=Boston&destination=Newark) (result **yes**)

 
`Philadelphia` and `Albany` are not connected

[http://localhost:8080/connected?origin=Philadelphia&destination=Albany](http://localhost:8080/connected?origin=Philadelphia&destination=Albany) (result **no**)