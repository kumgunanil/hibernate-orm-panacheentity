package com.anilit.resource;

import com.anilit.entity.LaptopEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/laptop")
public class LaptopResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptop(){
        List<LaptopEntity> listOfLaptop = LaptopEntity.listAll();
        return Response.ok(listOfLaptop).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLaptop(LaptopEntity laptop) {
        LaptopEntity.persist(laptop);
        if (laptop.isPersistent()) {
            return Response.created(URI.create("/laptop/" + laptop.id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLaptopById(@PathParam("id") Long id) {
        LaptopEntity laptop = LaptopEntity.findById(id);
        return Response.ok(laptop).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id") Long id, LaptopEntity updateLaptop) {
        Optional<LaptopEntity> optionalLaptop = LaptopEntity.findByIdOptional(id);
        if (optionalLaptop.isPresent()){
            LaptopEntity dbLaptop = optionalLaptop.get();

            if (Objects.nonNull(updateLaptop.getName())) {
                dbLaptop.setName(updateLaptop.getName());
            }
            if (Objects.nonNull(updateLaptop.getBrand())) {
                dbLaptop.setBrand(updateLaptop.getBrand());
            }
            if (Objects.nonNull(updateLaptop.getRam())) {
                dbLaptop.setRam(updateLaptop.getRam());
            }
            if (Objects.nonNull(updateLaptop.getExternalStorage())) {
                dbLaptop.setExternalStorage(updateLaptop.getExternalStorage());
            }

            dbLaptop.persist();
            if (dbLaptop.isPersistent()) {
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
        boolean isDeleted = LaptopEntity.deleteById(id);
        if (isDeleted) {
            return Response.noContent().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
