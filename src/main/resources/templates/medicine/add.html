<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Add Medicine</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; }
        .sidebar { width: 260px; height: 100vh; position: fixed; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #fff; padding-top: 24px; }
        .sidebar h4 { text-align: center; margin-bottom: 20px; }
        .sidebar a { display:block; padding:12px 22px; color: rgba(255,255,255,0.85); text-decoration:none; border-left: 4px solid transparent; }
        .sidebar a:hover, .sidebar a.active { background: rgba(255,255,255,0.1); color: #fff; border-left-color: #fff; }
        .content { margin-left: 260px; padding: 24px; }
        .card { border-radius: 16px; box-shadow: 0 6px 20px rgba(0,0,0,0.08); }
        .section-title { font-weight: 700; color: #334155; margin-bottom: 8px; }
        .muted { color: #64748b; }
        .form-icon { width: 42px; display:flex; align-items:center; justify-content:center; background:#fff; border-right:0; }
        .form-control, .form-select { padding: 10px 12px; }
        .btn-primary { background: linear-gradient(45deg, #2563eb, #1d4ed8); border: none; }
        .btn-outline-secondary { border-radius: 10px; }
    </style>
</head>
<body>
<div class="sidebar">
    <h4><i class="fas fa-pills me-2"></i>Pharmly</h4>
    <a href="/pharmacist/dashboard"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>
    <a href="/prescription/pharmacist/orders"><i class="fas fa-prescription-bottle-alt me-2"></i> Scheduled Orders</a>
    <a href="/pharmacist/inventory" class="active"><i class="fas fa-capsules me-2"></i> Medicine Inventory</a>
    <a href="/pharmacist/patients"><i class="fas fa-users me-2"></i> Patients</a>
    <a href="/pharmacist/login" class="text-warning mt-2"><i class="fas fa-sign-out-alt me-2"></i> Logout</a>
</div>

<div class="content">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <h3 class="mb-1"><i class="fas fa-plus me-2 text-primary"></i>Add Medicine</h3>
            <p class="muted mb-0">Create a new medicine item for the inventory</p>
        </div>
        <a href="/medicine/list" class="btn btn-outline-secondary"><i class="fas fa-arrow-left me-1"></i>Back to Inventory</a>
    </div>

    <form th:action="@{/medicine/add}" method="post" th:object="${medicine}" class="card p-4">
        <div class="row g-4">
            <div class="col-12">
                <div class="section-title">Basic Information</div>
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Medicine Name</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-capsules"></i></span>
                            <input class="form-control" th:field="*{medicineName}" placeholder="e.g., Amoxicillin" required>
                        </div>
                        <small class="muted">Official product name as on the label</small>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Generic Name</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-flask"></i></span>
                            <input class="form-control" th:field="*{genericName}" placeholder="e.g., Amoxicillin" required>
                        </div>
                        <small class="muted">Active pharmaceutical ingredient (API)</small>
                    </div>
                </div>
            </div>

            <div class="col-12">
                <div class="section-title">Classification</div>
                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Category</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-tags"></i></span>
                            <input class="form-control" th:field="*{category}" placeholder="e.g., Antibiotic" required>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Dosage Form</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-tablets"></i></span>
                            <input class="form-control" th:field="*{dosageForm}" placeholder="e.g., Tablet" required>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Strength</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-weight-hanging"></i></span>
                            <input class="form-control" th:field="*{strength}" placeholder="e.g., 500 mg" required>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12">
                <div class="section-title">Inventory & Pricing</div>
                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Unit Price</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-dollar-sign"></i></span>
                            <input type="number" step="0.01" class="form-control" th:field="*{unitPrice}" placeholder="e.g., 2.50">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Stock Quantity</label>
                        <div class="input-group">
                            <span class="input-group-text form-icon"><i class="fas fa-warehouse"></i></span>
                            <input type="number" class="form-control" th:field="*{stockQuantity}" placeholder="e.g., 150">
                        </div>
                    </div>
                    <div class="col-md-4 d-flex align-items-center">
                        <div class="form-check mt-4">
                            <input class="form-check-input" type="checkbox" th:field="*{isPrescriptionRequired}" id="rxRequired">
                            <label class="form-check-label" for="rxRequired"><i class="fas fa-prescription-bottle-medical me-1"></i>Prescription Required</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-4 d-flex gap-2 justify-content-end">
            <a href="/medicine/list" class="btn btn-outline-secondary"><i class="fas fa-times me-1"></i>Cancel</a>
            <button class="btn btn-primary" type="submit"><i class="fas fa-save me-1"></i>Save Medicine</button>
        </div>
    </form>
</div>
</body>
</html>




