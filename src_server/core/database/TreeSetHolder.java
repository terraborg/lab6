package core.database;

import core.HumanBeing;
import core.file.FileIn;
import core.file.FileOut;
import core.utils.GeneratorID;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Volovich Alexey
 * Класс управляющий коллекцией, реализует интерфейс DataBaseHolder
 * @see DataBaseHolder
 */
public class TreeSetHolder implements DataBaseHolder{
    private TreeSet<HumanBeing> collection;
    private final LocalDateTime creationTime;

    private final int maxSize = 100_000;

    private int size = 0;

    private final Comparator<HumanBeing> nameComp = new Comparator<HumanBeing>() {
        @Override
        public int compare(HumanBeing o1, HumanBeing o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName());
        }
    };
    private final FileIn fileIn;
    private final FileOut fileOut;
    public TreeSetHolder(FileIn fileIn, FileOut fileOut) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        creationTime = LocalDateTime.now();
        collection = new TreeSet<>();
    }


    @Override
    public DataBaseInfo getInfo() {
        String type = "TreeSet";
        return new DataBaseInfo(type,creationTime,size,maxSize);
    }

    @Override
    public void clear() {
        GeneratorID.setId(1L);
        collection.clear();
        size = 0;
    }

    @Override
    public boolean removeById(Long id) {
        if(!contains(id))
            return false;
        var stream = collection.stream().filter(e -> e.getId().longValue() != id.longValue());
        collection = stream.collect(Collectors.toCollection(TreeSet::new));
        size--;
        return true;
    }

    @Override
    public boolean contains(Long id) {
        return collection.stream().anyMatch(e -> e.getId() == id);
    }

    @Override
    public HumanBeing[] getAllElements() {
        return collection.stream().sorted(nameComp).toArray(HumanBeing[]::new);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void add(HumanBeing humanBeing) throws ElementsLimitReachedException {
        if(getSize()==maxSize)
            throw new ElementsLimitReachedException("в "+maxSize+" элементов");
        humanBeing.generateInfo(this);
        collection.add(humanBeing);
        size++;
    }

    @Override
    public void setAllElements(HumanBeing[] e) throws ElementsLimitReachedException {
        collection.clear();
        collection = Arrays.stream(e).limit(maxSize).collect(Collectors.toCollection(TreeSet<HumanBeing>::new));
        size = collection.size();
        if(e.length>maxSize)
            throw new ElementsLimitReachedException(" считано "+maxSize+"элементов");
    }

    @Override
    public int compareToMin(HumanBeing humanBeing) {
        try{
            return humanBeing.compareTo(collection.first());
        }
        catch (NoSuchElementException e)
        {
            return -1;
        }
    }

    @Override
    public int compareToMax(HumanBeing humanBeing) {
        try{
            return humanBeing.compareTo(collection.last());
        }
        catch (NoSuchElementException e)
        {
            return 1;
        }
    }

    @Override
    public int countByFilter(Predicate<HumanBeing> filter) {
        return (int) collection.stream().filter(filter).count();
    }

    @Override
    public HumanBeing[] getByFilter(Predicate<HumanBeing> filter) {
        return collection.stream().filter(filter).sorted(nameComp).toArray(HumanBeing[]::new);
    }

    @Override
    public boolean updateById(Long id, HumanBeing humanBeing) {
        if(!contains(id))
            return false;
        collection = collection.stream().peek(e-> {
            if(e.getId().equals(id))
                e.update(humanBeing);
        }).collect(Collectors.toCollection(TreeSet::new));
        return true;
    }

    public FileOut getFileOut() {
        return fileOut;
    }

    public FileIn getFileIn() {
        return fileIn;
    }
}
