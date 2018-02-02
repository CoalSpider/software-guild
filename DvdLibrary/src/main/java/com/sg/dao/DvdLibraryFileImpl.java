/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.Util;
import com.sg.model.Dvd;
import com.sg.model.Mpaar;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Ben Norman
 */
public class DvdLibraryFileImpl implements DvdLibrary {

    private static final String FILE_NAME = "dvdLibrary.txt";
    private static final String DELIMITER = "::";
    private static final Pattern PATTERN = Pattern.compile(DELIMITER);
    private static final int DVD_DELIMITER_COUNT = 5;

    // tree map so we always display in alphabetic order
    private Map<String, Dvd> library;

    /**
     * checks to make sure there are the proper number of delimters in each
     * file*
     */
    private boolean isValidFileLine(String line) {
        Matcher matcher = PATTERN.matcher(line);
        int count = 0;
        int from = 0;
        while (matcher.find(from)) {
            count++;
            from = matcher.start() + DELIMITER.length();
        }
        return count == DVD_DELIMITER_COUNT;
    }

    /**
     * reads dvdLibrary.txt into the library map *
     */
    private void readLibrary() throws DvdLibraryException {
        // we only want to read it in once not on every command like in the
        // code along
        if (library != null) {
            return;
        } else {
            // tree map so we stay in alphabetic order
            library = new TreeMap<>();
        }

        // use auto closing try with resources
        // force scanner to use buffered reader instead of default steam
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(FILE_NAME)))) {
            // init library
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (isValidFileLine(line) == false) {
                    throw new DvdLibraryException("Error reading dvd: " + line.split(DELIMITER)[0]);
                }

                String[] parts = line.split(DELIMITER);
                Dvd dvd = new Dvd(parts[0]);
                dvd.setReleaseDate(LocalDate.parse(parts[1], Util.DATE_FORMAT));
                dvd.setRating(Mpaar.valueOf(parts[2]));
                dvd.setDirectorsName(parts[3]);
                dvd.setStudio(parts[4]);
                dvd.setNote(parts[5]);
                // reuse our method
                addDvd(dvd.getTitle(), dvd);
            }
        } catch (FileNotFoundException ex) {
            throw new DvdLibraryException("Could not find dvd library to load");
        }
    }

    /**
     * Writes the library map into dvdLibrary.txt*
     */
    private void writeLibrary() throws DvdLibraryException {
        readLibrary();

        // TODO: only write lines that are different
        try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
            StringBuilder builder = new StringBuilder();
            for (Dvd dvd : listDvds()) {
                builder.append(dvd.getTitle());
                builder.append(DELIMITER);
                // localDate.format(dateTimeformatter)
                builder.append(dvd.getReleaseDate().format(Util.DATE_FORMAT));
                builder.append(DELIMITER);
                builder.append(dvd.getRating().toString());
                builder.append(DELIMITER);
                builder.append(dvd.getDirectorsName());
                builder.append(DELIMITER);
                builder.append(dvd.getStudio());
                builder.append(DELIMITER);
                // append a space if the user decided to not enter anything this is to deal with String.split stripping blank trailing stings
                String note = dvd.getNote();
                builder.append(note.length() == 0 ? " " : note);
                // write to file w/ line terminator
                pw.println(builder.toString());
                // force write to file right away
                pw.flush();
                // clear builder
                builder.delete(0, builder.length());
            }
        } catch (FileNotFoundException ex) {
            // should never happen as we always read in the file before writing anything
            throw new DvdLibraryException("Could not find dvd library to save to");
        }
    }

    @Override
    public Dvd addDvd(String title, Dvd dvd) throws DvdLibraryException {
        readLibrary();
        Dvd added = library.put(title, dvd);
        writeLibrary();
        return added;
    }

    @Override
    public Dvd removeDvd(String title) throws DvdLibraryException {
        readLibrary();
        Dvd removed = library.remove(title);
        writeLibrary();
        return removed;
    }

    @Override
    public Dvd editDvd(String title, Dvd newData) throws DvdLibraryException {
        readLibrary();
        Dvd edited = library.replace(title, newData);
        writeLibrary();
        return edited;
    }

    @Override
    public List<Dvd> listDvds() throws DvdLibraryException {
        readLibrary();
        return new ArrayList<>(library.values());
    }

    @Override
    public Dvd getDvd(String title) throws DvdLibraryException {
        readLibrary();
        return library.get(title);
    }

    @Override
    public List<String> listTitles() throws DvdLibraryException {
        readLibrary();
        return new ArrayList<>(library.keySet());
    }

    @Override
    public List<Dvd> searchForDvd(String search) throws DvdLibraryException {
//        search = search.toLowerCase();
//        List<Dvd> containingSearch = new ArrayList<>();
//        for (Dvd d : listDvds()) {
//            if (d.getTitle().toLowerCase().contains(search)
//                    || d.getRating().toString().toLowerCase().contains(search)
//                    || d.getDirectorsName().toLowerCase().contains(search)
//                    || d.getStudio().toLowerCase().contains(search)
//                    || d.getNote().toLowerCase().contains(search)) {
//                containingSearch.add(d);
//                
//            }
//        }
//        return containingSearch;
        final String search2 = search.toLowerCase();
        return listDvds()
                .stream()
                .filter((dvd) -> 
                        (dvd.getTitle().toLowerCase().contains(search2))
                      || dvd.getRating().toString().toLowerCase().contains(search2)
                      || dvd.getDirectorsName().toLowerCase().contains(search2)
                      || dvd.getStudio().toLowerCase().contains(search2)
                      || dvd.getNote().toLowerCase().contains(search2))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdReleasedInLast(int years) throws DvdLibraryException {
        int past = LocalDate.now().getYear() - years;
        return listDvds()
                .stream()
                .filter((dvd) -> dvd.getReleaseDate().getYear() >= past)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdWithRating(Mpaar rating) throws DvdLibraryException {
        return listDvds()
                .stream()
                .filter((dvd) -> dvd.getRating().equals(rating))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdsWithDirector(String directorName) throws DvdLibraryException {
        return listDvds()
                .stream()
                .filter((dvd) -> dvd.getDirectorsName().equalsIgnoreCase(directorName))
                .sorted((a, b) -> a.getRating().compareTo(b.getRating()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdWithStudio(String studio) throws DvdLibraryException {
        return listDvds()
                .stream()
                .filter((dvd) -> dvd.getStudio().equalsIgnoreCase(studio))
                .collect(Collectors.toList());
    }

    @Override
    public int getAverageAge() throws DvdLibraryException {
        List<Dvd> dvds = listDvds();
        if (dvds.isEmpty()) { // prevent divide by zero error
            throw new DvdLibraryException("Library Is Empty");
        }
        return dvds
                .stream()
                .mapToInt((dvd) -> LocalDate.now().getYear() - dvd.getReleaseDate().getYear())
                .sum() // could use average
                / dvds.size();
    }

    @Override
    public Dvd getNewestDvds() throws DvdLibraryException {
        List<Dvd> dvds = listDvds();
        return listDvds()
                .stream()
                .reduce(dvds.get(0), (a, b) -> a.getReleaseDate().isAfter(b.getReleaseDate()) ? a : b);
    }

    @Override
    public Dvd getOldestDvds() throws DvdLibraryException {
        List<Dvd> dvds = listDvds();
        return listDvds()
                .stream()
                .reduce(dvds.get(0), (a, b) -> a.getReleaseDate().isBefore(b.getReleaseDate()) ? a : b);
    }

    @Override
    public int getAverageNoteLength() throws DvdLibraryException {
        List<Dvd> dvds = listDvds();
        if (dvds.isEmpty()) { // prevent divide by zero error
            throw new DvdLibraryException("Library Is Empty");
        }
        return dvds
                .stream()
                .mapToInt((dvd) -> dvd.getNote().length()) // could use average
                .sum()
                / dvds.size();
    }
}
