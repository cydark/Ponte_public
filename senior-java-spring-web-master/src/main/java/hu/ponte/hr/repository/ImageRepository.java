package hu.ponte.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ponte.hr.controller.ImageMeta;

@Repository
public interface ImageRepository extends JpaRepository<ImageMeta, String> {

}
