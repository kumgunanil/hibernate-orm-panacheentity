package com.anilit.resource;

import com.anilit.entity.LaptopEntity;
import com.anilit.entity.LaptopEntityForIntr;
import com.anilit.repository.LaptopRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/new laptop")
public class LaptopResourceIntr {

    @Inject
    LaptopRepository laptopRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptop(){
        List<LaptopEntityForIntr> listOfLaptop = laptopRepository.listAll();
        return Response.ok(listOfLaptop).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLaptop(LaptopEntityForIntr laptop) {
        laptopRepository.persist(laptop);
        if (laptopRepository.isPersistent(laptop)) {
            return Response.created(URI.create("/laptop/" + laptop.id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLaptopById(@PathParam("id") Long id) {
        LaptopEntityForIntr laptop = laptopRepository.findById(id);
        return Response.ok(laptop).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id") Long id, LaptopEntity updateLaptop) {
        Optional<LaptopEntityForIntr> optionalLaptop = laptopRepository.findByIdOptional(id);
        if (optionalLaptop.isPresent()){
            LaptopEntityForIntr dbLaptop = optionalLaptop.get();

            if (Objects.nonNull(updateLaptop.getName())) {
                dbLaptop.setName(updateLaptop.getName());
            }
            if (Objects.nonNull(updateLaptop.getBrand())) {
                dbLaptop.setBrand(updateLaptop.getBrand());
            }
            if (updateLaptop.getRam() != 0) {
                dbLaptop.setRam(updateLaptop.getRam());
            }
            if (updateLaptop.getExternalStorage() != 0) {
                dbLaptop.setExternalStorage(updateLaptop.getExternalStorage());
            }

            laptopRepository.persist(dbLaptop);
            if (laptopRepository.isPersistent(dbLaptop)) {
                return Response.created(URI.create("/laptop/" + id)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptop(@PathParam("id") Long id) {
        boolean isDeleted = laptopRepository.deleteById(id);
        if (isDeleted) {
            return Response.noContent().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
