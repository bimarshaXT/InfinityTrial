/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.infinitytrailapp.algorithm;

import com.infinitytrailapp.model.CandidateModel;
import java.util.List;

/**
 *
 * @author ACER
 */
public class Binarysort {
    public static CandidateModel binarySearchByCandidateNo(int candidateNo, List<CandidateModel> candidates, int left, int right) {
        if (right < left) {
            return null;
        }

        int mid = (left + right) / 2;

        if (candidates.get(mid).getCandidateNo() == candidateNo) {
            return candidates.get(mid);
        } else if (candidateNo < candidates.get(mid).getCandidateNo()) {
            return binarySearchByCandidateNo(candidateNo, candidates, left, mid - 1);
        } else {
            return binarySearchByCandidateNo(candidateNo, candidates, mid + 1, right);
        }
    }

    // Binary Search by Name
    public static CandidateModel binarySearchByName(String name, List<CandidateModel> candidates, int left, int right) {
        if (right < left) {
            return null;
        }

        int mid = (left + right) / 2;

        int comparison = name.compareToIgnoreCase(candidates.get(mid).getName());

        if (comparison == 0) {
            return candidates.get(mid);
        } else if (comparison < 0) {
            return binarySearchByName(name, candidates, left, mid - 1);
        } else {
            return binarySearchByName(name, candidates, mid + 1, right);
        }
    }
}

