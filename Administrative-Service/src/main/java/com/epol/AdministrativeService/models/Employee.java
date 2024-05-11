package com.epol.AdministrativeService.models;

public record Employee(Long id, Long departmentId, String name, int age, String position) {
}
