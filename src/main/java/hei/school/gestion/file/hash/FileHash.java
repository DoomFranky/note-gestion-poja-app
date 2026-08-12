package hei.school.gestion.file.hash;

import hei.school.gestion.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
