package com.can.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/personservice")
public class JSONService {
	
	private static ArrayList<Person> personList = new ArrayList<Person>();

	@GET
	@Path("/get/{param}")
	public Response getPerson(@PathParam("param") int id) {
		for(Person p: personList) {
			if(p.getId() ==id)
				return Response.status(200).entity(p).build();
		}

		String output = "Failed";
		return Response.status(404).entity(output).build();
		
	}
	
	@GET
	@Path("/getall")
	public Response getAll() {
		Gson gson = new Gson();
		String json = gson.toJson(personList);
		return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("/add")
	public Response addPerson() {
		Person p1 = new Person();
		p1.setAge(21);
		p1.setId(1);
		p1.setName("can");
		
		personList.add(p1);
		
		Person p2 = new Person();
		p2.setAge(21);
		p2.setId(2);
		p2.setName("dogan");
		
		personList.add(p2);
		
		String output = "Added: " + p1 + "\n" + p2;
		
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/delete/{param}")
	public Response deletePerson(@PathParam("param") int id) {
		String output = null;
		
		for(Person p : personList) {
			if(p.getId() == id) {
				personList.remove(p);
				output = "Removed: " + p;
				return Response.status(200).entity(output).build();
			}
		}
		output = "Failed";
		return Response.status(404).entity(output).build();
	}
	
//	@GET
//	@Path("/{param}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Person getPersonInJSON(@PathParam("param") String id) {
//
//		for(Person p: personList) {
//			if(p.getId() ==Integer.parseInt(id))
//				return p;
//		}
//		return null;
////		Person person = new Person();
////		person.setName("AAA");
////		person.setId(1);
////		person.setAge(1);
////
////		return person;
//
//	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPersonInJSON(Person person) {
		
		// Check if the id is free
		for(Person p: personList) {
			if(p.getId() == person.getId()) {
				p.setAge(person.getAge());
				p.setId(person.getId());
				p.setName(person.getName());
				String result = "Person edited : " + person;
				return Response.status(201).entity(result).build();
			}
		}
		
		// Create the object and add it into the list
		Person personnel = new Person();
		personnel.setAge(person.getAge());
		personnel.setId(person.getId());
		personnel.setName(person.getName());
		
		personList.add(personnel);

		String result = "Person saved : " + person;
		
		return Response.status(201).entity(result).build();

	}

}
