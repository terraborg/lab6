package core.file;

import core.HumanBeing;
import core.database.DataBaseHolder;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

/**
 * @author Volovich Alexey
 * Класс записывающий объекты класса HumanBeing в XML файл.
 * @see HumanBeing
 */
public class FromObjectToXML implements FileOut{
    /**
     * Поле хранящее путь к записываемому файлу.
     */
    private final String path;

    /**
     * Конструктор, в который передается путь к считываемому файлу.
     * @param path
     */
    public FromObjectToXML(String path) {
        this.path = path;
    }

    /**
     * Основная команда класса, производящая запись коллекции в XML файл.
     * В параметрах передается ссылка на коллекцию из которой будут считаны данные.
     * @param collection
     * @throws IOException
     * @throws XMLStreamException
     */
    @Override
    public void writeCollection(DataBaseHolder collection) throws XMLStreamException, IOException {
        if(path == null)
            throw new NullPointerException();
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        var file = new File(path);
        if (!file.exists())
            file.createNewFile();
        if(!file.canWrite())
            throw new AccessDeniedException(path);
        XMLStreamWriter writer = output.createXMLStreamWriter(new OutputStreamWriter(new FileOutputStream(file)));
        writer.writeStartDocument("1.0");
        writer.writeStartElement(collection.getInfo().getType());
        for (HumanBeing e : collection.getAllElements()) {
            writer.writeStartElement("HumanBeing");

            writer.writeAttribute("id", String.valueOf(e.getId()));
            writer.writeAttribute("name", e.getName());
            writer.writeAttribute("realHero", e.getRealHero().toString());
            writer.writeAttribute("hasToothpick", e.getHasToothpick().toString());
            writer.writeAttribute("impactSpeed", String.valueOf(e.getImpactSpeed()));
            if (e.getWeaponType() != null)
                writer.writeAttribute("weaponType", e.getWeaponType().name());
            else
                writer.writeAttribute("weaponType", "");
            writer.writeAttribute("mood", e.getMood().name());

            writer.writeStartElement("CreationTime");
            LocalDateTime time = e.getCreationTime();
            writer.writeAttribute("date", time.toLocalDate().toString());
            writer.writeAttribute("time", time.toLocalTime().withNano(0).toString());
            writer.writeEndElement();

            writer.writeStartElement("Coordinates");
            writer.writeAttribute("x", String.valueOf(e.getCoordinates().getX()));
            writer.writeAttribute("y", String.valueOf(e.getCoordinates().getY()));
            writer.writeEndElement();

            writer.writeStartElement("Car");
            if (e.getCar() != null && e.getCar().getName() != null)
                writer.writeAttribute("name", e.getCar().getName());
            else
                writer.writeAttribute("name", "");
            if (e.getCar() != null && e.getCar().getName() != null)
                writer.writeAttribute("cool", e.getCar().getCool().toString());
            else
                writer.writeAttribute("cool", "");
            writer.writeEndElement();

            writer.writeEndElement();
        }
        writer.writeEndDocument();
        writer.close();
    }

}
