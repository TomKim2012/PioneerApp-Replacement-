package com.tomkimani.mgwt.demo.client.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

class StringLengthComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		int len = s1.length();
		int len2=s2.length();
		
		if(len>len2){
			return 1;
		}else if(len2>len){
			return -1;
		}
		return 0;
	}
	
}
class Person{
	private String name;
	private int id;
	private Date dateTime;

	public Person(int id, String name, Date dateTime) {
		this.id = id;
		this.name = name;
		this.setDateTime(dateTime);
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return name;
	}
}

class AlphabeticalComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		return s1.compareTo(s2);
	}
	
}

class ReverseAlphabeticalComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		return -s1.compareTo(s2);
	}
	
}

public class ListSort{
	public static void main(String[] args) {
		
		////////////////Sorting Strings ///////////////////
		List<String> animals = new ArrayList<String>();
		animals.add("Cat");
		animals.add("Monkey");
		animals.add("Elephant");
		animals.add("Donkey");
		
		Collections.sort(animals, new ReverseAlphabeticalComparator());
		for(String animal:animals){
			System.out.println(animal);
		}
		
		//////////////Sorting Integers ///////////////////
		List<Integer> numbers= new ArrayList<Integer>();
		numbers.add(3);
		numbers.add(90);
		numbers.add(85);
		numbers.add(75);
		numbers.add(50);
		numbers.add(30);
		Collections.sort(numbers);
		for(Integer number:numbers){
			System.out.println(number);
		}
		
		//////////////Sorting Other Objects ///////////////////
		List<Person> people =new ArrayList<Person>();
		people.add(new Person(1, "Joe", new Date()));
		people.add(new Person(2, "Sue", new Date(2009,11,6)));
		people.add(new Person(3, "Bob", new Date(2010,15,6)));
		people.add(new Person(4, "Njoroge", new Date()));
		
		Collections.sort(people, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getDateTime().compareTo(o2.getDateTime());
			}
			
		});
		
		Collections.sort(people, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getName().compareTo(o2.getName());
			}
			
		});
		
		for (Person person : people) {
			System.out.println(person.getId()+":: "+person.toString());
		}
		
	}
}
