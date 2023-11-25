package main.utils.iocontrol;

import main.model.user.Committee;
import main.model.user.Staff;
import main.model.user.Student;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.parameters.EmptyID;


import java.sql.SQLOutput;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.*;

public interface Mappable {
    default Map<String, String> toMap(){
        Map<String,String> map = new HashMap<>();
        Class<?> currentClass = getClass();
        boolean a= true;
        while(a){
            Field[] fields = currentClass.getDeclaredFields();
            for(Field field:fields){
                try{
                    field.setAccessible(true);
                    if(field.getName().equals("campIdTracker")||field.getName().equals("enquiryIDTracker")){}
                    else {
                        try {
                            map.put(field.getName(), field.get(this).toString());
                        } catch (NullPointerException e) {
                            map.put(field.getName(), EmptyID.EMPTY_ID);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            if(currentClass== Committee.class){
                currentClass = Student.class;
            }
            else{
                a = false;
            }

        }

        return map;
    }

    // fromMap converts MAP to object
//    default void fromMap(Map<String,String> map){
////        if(getClass()== Committee.class)
//        Field[] fields = getClass().getDeclaredFields();
//
//        for(Field field:fields){
//            try{
//                field.setAccessible(true);
//                if (field.getType().isEnum()) {
//                    @SuppressWarnings("unchecked")
//                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), map.get(field.getName()));
//                    field.set(this, enumValue);
//                }
//                else if(field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)){
//                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
//                        field.set(this, 0);
//                    } else {
//                        int intValue = Integer.parseInt(map.get(field.getName()));
//                        field.set(this, intValue);
//                    }
//                }
//                else if (field.getType().equals(LocalDate.class)) {
//                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
//                        field.set(this, null);
//                    } else {
//                        LocalDate someDate = LocalDate.parse(map.get(field.getName()));
//                        field.set(this, someDate);
//                    }
//                }
//                else if (field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
//                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
//                        field.set(this, false); // Default value for boolean can be set to false
//                    } else {
//                        boolean booleanValue = Boolean.parseBoolean(map.get(field.getName()));
//                        field.set(this, booleanValue);
//                    }
//                }
//                else if(field.getName().equals("campIdTracker")||field.getName().equals("enquiryIDTracker")){}
//                else if (field.getType().equals(List.class)) {
//                    String concatenatednames = map.get(field.getName());
//                    ArrayList<String> listOfNames = new ArrayList<>();
//                    if(concatenatednames.length()==0){
//                        field.set(this,listOfNames);
//                    }
//                    String stringWithoutBrackets = concatenatednames.substring(1,concatenatednames.length()-1);
//                    String[] elements = stringWithoutBrackets.split(",");
//                    for (int i = 0; i < elements.length; i++) {
//                        elements[i] = elements[i].trim();
//                        listOfNames.add(elements[i]);
//                    }
//                    field.set(this,listOfNames);
//                }
//                else {
//                    field.set(this, map.get(field.getName()));
//                }
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    default void fromMap(Map<String,String> map){
        Class<?> currentClass = getClass();
        boolean a= true;
        while(a){
            Field[] fields = currentClass.getDeclaredFields();
            for(Field field:fields){
            try{
                field.setAccessible(true);
                if (field.getType().isEnum()) {
                    @SuppressWarnings("unchecked")
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), map.get(field.getName()));
                    field.set(this, enumValue);
                }
                else if(field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)){
                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
                        field.set(this, 0);
                    } else {
                        int intValue = Integer.parseInt(map.get(field.getName()));
                        field.set(this, intValue);
                    }
                }
                else if (field.getType().equals(LocalDate.class)) {
                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
                        field.set(this, null);
                    } else {
                        LocalDate someDate = LocalDate.parse(map.get(field.getName()));
                        field.set(this, someDate);
                    }
                }
                else if (field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
                        field.set(this, false); // Default value for boolean can be set to false
                    } else {
                        boolean booleanValue = Boolean.parseBoolean(map.get(field.getName()));
                        field.set(this, booleanValue);
                    }
                }
                else if(field.getName().equals("campIdTracker")||field.getName().equals("enquiryIDTracker")){}
                else if (field.getType().equals(List.class)) {
//                    System.out.println("class enti"+currentClass);
                    String concatenatednames = map.get(field.getName());
                    ArrayList<String> listOfNames = new ArrayList<>();
                    if(concatenatednames.length()==0){
                        field.set(this,listOfNames);
                    }
                    String stringWithoutBrackets = concatenatednames.substring(1,concatenatednames.length()-1);
                    String[] elements = stringWithoutBrackets.split(",");
                    for (int i = 0; i < elements.length; i++) {
                        elements[i] = elements[i].trim();
                        listOfNames.add(elements[i]);
                    }
                    field.set(this,listOfNames);
                }
                else {
                    field.set(this, map.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
            if(currentClass== Committee.class){
                currentClass = Student.class;
            }
            else{
                a = false;
            }

        }

    }

    }
