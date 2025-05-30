package com.ppstudios.footballmanager.api.contracts.match;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;

/**
 * Simula um único IMatch, gerando eventos (golos, faltas, etc) via IMatch.addEvent(...)
 */
public interface IMatchSimulator {
    void simulateMatch(IMatch match);
}
