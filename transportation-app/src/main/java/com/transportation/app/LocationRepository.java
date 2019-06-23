package com.transportation.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.context.annotation.Bean;

interface LocationRepository extends JpaRepository<Location, Long> {

}

