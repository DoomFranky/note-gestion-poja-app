package hei.school.gestion.endpoint.event.consumer.model;

import hei.school.gestion.PojaGenerated;
import hei.school.gestion.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
