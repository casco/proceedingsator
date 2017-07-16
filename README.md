Proceedingstator
================
Proceedingsator ayuda a compaginar los proceedings de los simposios de las JAIIO. 
Toma información de los articulos y los pdfs originales. Agrega encabezado y pie de página a los pdfs y genera una
pagina web básica que sirve de índice. 

Instalación
-----------
Proceedingsator es una aplicación java, basada en Maven. Para construir la aplicación, simplemente ejecute:
```
mvn package
```
Dos versiones del jar serán generadas. Una de ella incluye todas las dependencias necesarias
(```proceedingsator-X.Y-jar-with-dependencies.jar```). Cambie el nombre a ```proceedingsator.jar``` y ya estará listo para 
comenzar. 

Si lo prefiere, puede descargar la versión mas reciente, ya empaquetada, [desde este enlace](https://github.com/casco/proceedingsator/blob/master/proceesingsator.jar)


Preparación
-----------
Prepare (en una carpeta/directorio) los pdf originales de los articulos a incluir en el proceeding. Proceedingsator 
asume que los mismos estan en  formato A4. Se sugiere que los archivos tengan nombre ACRONIMO-XX.pdf donde acrónimo 
se refiere al nombre corto del  simposio (p.e., ASSE) y XX es un identificador unico para ese articulo. 
 
Prepare un archivo de texto separado por comas (.CSV) el listado de los articulos. Incluya en el mismo
tres columnas con estos encabezados: "titulo,autores,archivo". Todas las columnas deben estar delimitadas por 
comillas para evitar inconvenientes. Vea el siguiente ejemplo:

    titulo,autores,documento
    "Regresión con SVM la matriz AHP.","Favret, F. E., Rodríguez, F. M., Labat, M. D.",asai1.pdf
    "Knowledge Representation of Intelligent Public.","Brys, C. R., Aldana-Montes, J. F.",asai2.pdf
    
Alternativamente (y para evitar los inconvenientes de preparar un archivo csv correctamente delimitado, puede 
utilizar como entrada un archivo Excel 97/2004, con extensión xls. 
  
Uso
---
Proceedingstator es un jar (Java) ejecutable. La estrategia general se uso es:

    java -jar proceedingstator.jar parámetros

Ejecutarlo sin parametros hará que imprima información de ayuda. Los parámetros disponibles son:

     -a <arg>   acrónimo del simposio  (p.e. SIA)
     -c <arg>   nombre corto de la conferencia (p.e. 45JAIIO)
     -i <arg>   issn del simposio  (p.e. 2451-7585)
     -l <arg>   archivo CSV con el listado de articulos (p.e.
                ./originales/lista.csv)
     -o <arg>   ubicación de los documentos resultantes (p.e. ./numerados)
     -p <arg>   ubicación de los documentos pdf (p.e. ./originales)
     -s <arg>   nombre completo del simposio (p.e. "Simposio De Informática")
     
Ejemplo
     
     
    java -jar proceedingsator.jar -a "ASAI" -c "44JAIIO" -i 2451-7585 -l originales/articles.csv \
         -o listos -p originales -s "Simposio de Inteligencia Artificial"
