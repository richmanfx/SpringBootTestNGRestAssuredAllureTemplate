package ru.r5am.tests.smoke;

import io.qameta.allure.Owner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import ru.r5am.entities.Stand;
import ru.r5am.basetest.ApiBaseTest;

/**
 * Smoke-тесты стенда
 */
@Slf4j
@Owner("Ящук Александр Юрьевич")
public class SmokeStand extends ApiBaseTest {

    @Autowired private Stand stand;


}
