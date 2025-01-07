
package com.infinitytrailapp.controller;

/**
 *
 * @author Bimarsha
 */
import com.infinitytrailapp.model.CandidateModel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

public class CandidateQueueProcessor {

    private Queue<CandidateModel> trialQueue;
    private DefaultTableModel resultsTableModel;

    public CandidateQueueProcessor(DefaultTableModel resultsTableModel) {
        this.trialQueue = new LinkedList<>();
        this.resultsTableModel = resultsTableModel;
    }

    // Add a candidate to the queue
    public void enqueueCandidate(CandidateModel candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate cannot be null.");
        }
        trialQueue.add(candidate);
    }

    // Peek at the next candidate
    public CandidateModel peekCandidate() {
        if (trialQueue.isEmpty()) {
            throw new IllegalStateException("No candidates in the queue.");
        }
        return trialQueue.peek();
    }

    // Process the next candidate in the queue
    public void startTrial() {
        if (trialQueue.isEmpty()) {
            throw new IllegalStateException("No candidates to process.");
        }

        Random random = new Random();
        CandidateModel candidate = trialQueue.poll(); // Remove the first candidate

        String status = random.nextBoolean() ? "Passed" : "Failed";
        String licenseNumber = status.equals("Passed") 
                               ? String.valueOf(100000 + random.nextInt(900000)) 
                               : "NA";

        // Add the result to the table
        resultsTableModel.addRow(new Object[]{
            candidate.getCandidateNo(),
            candidate.getName(),
            candidate.getContact(),
            candidate.getCategory(),
            candidate.getType(),
            candidate.getCitizenshipNo(),
            candidate.getDateOfBirth(),
            status,
            licenseNumber
        });
    }


    // Check if the queue is empty
    public boolean isQueueEmpty() {
        return trialQueue.isEmpty();
    }
}
