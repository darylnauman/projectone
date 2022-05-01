package com.ex.requestreimbursement.controllers;

import com.ex.requestreimbursement.models.Action;
import com.ex.requestreimbursement.models.ReimbursementRequest;
import com.ex.requestreimbursement.services.ReimbursementRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reimbursementrequsts")
public class ReimbursementRequestController {

    @Autowired
    ReimbursementRequestService reimbursementRequestService;

    @PostMapping
    public ResponseEntity createReimbursementRequest(@RequestBody ReimbursementRequest newReimbursementRequest) {
        try {
            boolean success = reimbursementRequestService.saveReimbursementRequest(newReimbursementRequest);

            if (success) {
                return ResponseEntity.ok("Reimbursement request created");
            } else {
                return ResponseEntity.internalServerError().body("Error saving reimbursement request");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
        }
    }

    @GetMapping
    public ResponseEntity getAllReimbursementRequests() {
        try {
            List<ReimbursementRequest> reimbursementRequests = reimbursementRequestService.findAllReimbursementRequests();
            return ResponseEntity.ok(reimbursementRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting all reimbursement requests");
        }
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity getAllReimbursementRequestsByManagerId(@PathVariable Integer managerId) {
        try {
            List<ReimbursementRequest> reimbursementRequests = reimbursementRequestService.findAllReimbursementRequestsByManagerId(managerId);
            return ResponseEntity.ok(reimbursementRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting all reimbursement requests by managerId");
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity getAllReimbursementRequestsByEmployeeId(@PathVariable Integer employeeId) {
        try {
            List<ReimbursementRequest> reimbursementRequests = reimbursementRequestService.findAllReimbursementRequestsByEmployeeId(employeeId);
            return ResponseEntity.ok(reimbursementRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting all reimbursement requests by employeeId");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getReimbursementRequestById(@PathVariable Integer id) {
        try {
            Optional<ReimbursementRequest> reimbursementRequest = reimbursementRequestService.findById(id);

            if(reimbursementRequest.isPresent()) {
                return ResponseEntity.ok(reimbursementRequest);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/{revisedManagerId}")
    public ResponseEntity reassignReimbursementRequest(@PathVariable Integer id, @PathVariable Integer revisedManagerId) {
        try {
            reimbursementRequestService.reassignReimbursementRequest(id, revisedManagerId);
            return ResponseEntity.ok().body("Success - reassigned to " + revisedManagerId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Reassign reimbursement request failed");
        }
    }

    @PutMapping("{id}/action")
    public ResponseEntity updateStatusReimbursementRequest(@PathVariable Integer id, @RequestBody Action action) {
        try {
            reimbursementRequestService.updateStatus(id, action);
            return ResponseEntity.ok().body("Success - updated status to " + action.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Update reimbursement request status failed");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteReimbursementRequest(@PathVariable Integer id) {
        try {
            boolean success = reimbursementRequestService.deleteReimbursementRequest(id);

            if (success) {
                return ResponseEntity.ok("Reimbursement request deleted");
            } else {
                return ResponseEntity.internalServerError().body("Error deleting reimbursement request");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting reimbursement request");
        }
    }
}