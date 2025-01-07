/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.infinitytrailapp.algorithm;

import com.infinitytrailapp.model.CandidateModel;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ACER
 */
public class InsertionSort {
        public static void insertionSortDate(List<CandidateModel> candidates, boolean ascending) {
        for (int i = 1; i < candidates.size(); i++) {
            CandidateModel key = candidates.get(i);
            LocalDate keyDate = LocalDate.parse(key.getDateOfBirth());
            int j = i - 1;

            while (j >= 0 && (ascending
                    ? LocalDate.parse(candidates.get(j).getDateOfBirth()).isAfter(keyDate)
                    : LocalDate.parse(candidates.get(j).getDateOfBirth()).isBefore(keyDate))) {
                candidates.set(j + 1, candidates.get(j));
                j--;
            }
            candidates.set(j + 1, key);
        }
    }
}
