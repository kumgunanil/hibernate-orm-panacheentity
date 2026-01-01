package com.anilit.repository;

import com.anilit.entity.LaptopEntity;
import com.anilit.entity.LaptopEntityForIntr;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LaptopRepository implements PanacheRepository<LaptopEntityForIntr> {
}
