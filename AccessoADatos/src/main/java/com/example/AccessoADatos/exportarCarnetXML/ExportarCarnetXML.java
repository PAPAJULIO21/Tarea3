package com.example.AccessoADatos.exportarCarnetXML;

import com.example.AccessoADatos.clases.Carnet;
import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.ClientInfoStatus;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExportarCarnetXML {

    EntrenadorService entrenadorService;

    CarnetService carnetService;

    TorneoService torneoService;

    CombateService combateService;

    public ExportarCarnetXML(EntrenadorService entrenadorService,CarnetService carnetService,TorneoService torneoService,CombateService combateService){
        this.carnetService = carnetService;
        this.entrenadorService = entrenadorService;
        this.combateService = combateService;
        this.torneoService = torneoService;
    }

    public void exportarCarnet(String id){
        long idEntrenador = Long.parseLong(id);

        Carnet carnet = carnetService.buscarCarnetPorId(idEntrenador);

        Entrenador entrenador = entrenadorService.buscarEntrenadorPorId(idEntrenador);

        List<Torneo> torneo0 = new ArrayList<>(entrenador.getTorneos());

        if (torneo0.isEmpty()){
            System.out.println("El entrenador no esta asignado a un torneo");
        }else{
            Torneo torneo1 = torneo0.get(0);
            Torneo torneo = torneoService.buscarTorneoPorId(torneo1.getId());

            // Filtrar combates donde el entrenador participe en entrenador1 o entrenador2
            List<Combate> combatesFiltrados = torneo.getCombates().stream()
                    .filter(combate ->
                            (combate.getEntrenador1() != null && combate.getEntrenador1().getId().equals(idEntrenador)) ||
                                    (combate.getEntrenador2() != null && combate.getEntrenador2().getId().equals(idEntrenador))
                    )
                    .toList();



            crearXMLCarnet(carnet,"/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/xml/"+entrenador.getNombre().toUpperCase()+".xml", combatesFiltrados,torneo);
            System.out.println("Se ha exportado el carnet perfectamente.");
        }




    }

    public void crearXMLCarnet(Carnet carnet, String archivoSalida, List<Combate> listaCombates, Torneo torneo) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Crear el elemento raíz <carnet>
            Element carnetElement = doc.createElement("carnet");
            doc.appendChild(carnetElement);

            // <id>
            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(carnet.getIdEntrenador())));
            carnetElement.appendChild(idElement);

            // <fechaexp>
            Element fechaexpElement = doc.createElement("fechaexp");
            fechaexpElement.appendChild(doc.createTextNode(carnet.getFechaExpedicion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            carnetElement.appendChild(fechaexpElement);

            // <entrenador>
            Element entrenadorElement = doc.createElement("entrenador");
            carnetElement.appendChild(entrenadorElement);

            // <nombre>
            Element nombreEntrenadorElement = doc.createElement("nombre");
            nombreEntrenadorElement.appendChild(doc.createTextNode(carnet.getEntrenador().getNombre()));
            entrenadorElement.appendChild(nombreEntrenadorElement);

            // <nacionalidad>
            Element nacionalidadElement = doc.createElement("nacionalidad");
            nacionalidadElement.appendChild(doc.createTextNode(carnet.getEntrenador().getNacionalidad()));
            entrenadorElement.appendChild(nacionalidadElement);

            // <hoy>
            Element hoyElement = doc.createElement("hoy");
            hoyElement.appendChild(doc.createTextNode(carnet.getFechaExpedicion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            carnetElement.appendChild(hoyElement);

            // <puntos>
            Element puntosElement = doc.createElement("puntos");
            puntosElement.appendChild(doc.createTextNode(String.valueOf(carnet.getPuntos())));
            carnetElement.appendChild(puntosElement);

            // <torneos>
            Element torneosElement = doc.createElement("torneos");
            carnetElement.appendChild(torneosElement);

            //<torneo>
            Element torneoElement = doc.createElement("torneo");
            torneosElement.appendChild(torneoElement);

            //<nombre>
            Element nombreElement = doc.createElement("nombre");
            nombreElement.appendChild(doc.createTextNode(torneo.getNombre()));
            torneoElement.appendChild(nombreElement);

            //<region>
            Element regionElement = doc.createElement("region");
            regionElement.appendChild(doc.createTextNode(String.valueOf(torneo.getCodRegion())));
            torneoElement.appendChild(regionElement);

            //<combates>
            Element combatesElement = doc.createElement("combates");
            torneoElement.appendChild(combatesElement);

            if (listaCombates != null) {
                for (Combate combate : listaCombates) {
                    Element combateElement = doc.createElement("combate");

                    // <id>
                    Element idCombateElement = doc.createElement("id");
                    idCombateElement.appendChild(doc.createTextNode(String.valueOf(combate.getId())));
                    combateElement.appendChild(idCombateElement);

                    // <fecha>
                    Element fechaCombateElement = doc.createElement("fecha");
                    fechaCombateElement.appendChild(doc.createTextNode(combate.getFecha().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    combateElement.appendChild(fechaCombateElement);

                    // <victoria>
                    Element victoriaCombateElement = doc.createElement("victoria");
                    // Verificar si el id del ganador del combate coincide con el id del entrenador
                    Long idGanadorCombate = combate.getGanador();

                    boolean esGanador = idGanadorCombate != null && idGanadorCombate.equals(carnet.getIdEntrenador());

                    // Asignar "true" o "false" al elemento <victoria>
                    victoriaCombateElement.appendChild(doc.createTextNode(String.valueOf(esGanador)));
                    combateElement.appendChild(victoriaCombateElement);

                    combatesElement.appendChild(combateElement);
                }
            } else {
                // Si listaCombates es null, agregar un combate vacío
                Element combateElement = doc.createElement("combate");

                Element idCombateElement = doc.createElement("id");
                combateElement.appendChild(idCombateElement);

                Element fechaCombateElement = doc.createElement("fecha");
                combateElement.appendChild(fechaCombateElement);

                Element victoriaCombateElement = doc.createElement("victoria");
                combateElement.appendChild(victoriaCombateElement);

                combatesElement.appendChild(combateElement);
            }

            // Guardar el XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Espacios de sangría
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoSalida));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException | DOMException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Error: Uno de los valores es nulo. Verifica los datos de Carnet, Entrenador o Torneo.");
            e.printStackTrace();
        }
    }
}
