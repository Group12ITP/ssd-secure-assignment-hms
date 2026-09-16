<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacist Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Inter', sans-serif;
        }
        .sidebar {
            width: 280px;
            height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            position: fixed;
            padding-top: 30px;
        }
        .sidebar h4 {
            font-weight: bold;
            text-align: center;
            color: #fff;
            margin-bottom: 30px;
        }
        .sidebar a {
            display: block;
            padding: 15px 25px;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            font-size: 16px;
            border-left: 4px solid transparent;
            transition: all 0.3s;
        }
        .sidebar a:hover, .sidebar a.active {
            background-color: rgba(255,255,255,0.1);
            color: #fff;
            border-left: 4px solid #fff;
        }
        .main-content {
            margin-left: 280px;
            padding: 30px;
        }
        .summary-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            padding: 25px;
            text-align: center;
            transition: transform 0.2s;
        }
        .summary-card:hover {
            transform: translateY(-5px);
        }
        .summary-number {
            font-size: 2.5rem;
            font-weight: 700;
            margin-bottom: 10px;
        }
        .order-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            margin-bottom: 20px;
            overflow: hidden;
            transition: all 0.3s;
        }
        .order-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.12);
        }
        .order-header {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            padding: 20px;
        }
        .order-body {
            padding: 20px;
        }
        .status-badge {
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
        }
        .status-pending { background-color: #fff3cd; color: #856404; }
        .status-processing { background-color: #cce5ff; color: #004085; }
        .status-ready { background-color: #d4edda; color: #155724; }
        .status-completed { background-color: #d1ecf1; color: #0c5460; }
        .medicine-item {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 10px;
            border-left: 4px solid #007bff;
        }
        .urgent-badge {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: 600;
        }
        .action-btn {
            border-radius: 8px;
            padding: 8px 16px;
            font-weight: 600;
            transition: all 0.3s;
        }
        .btn-process {
            background: linear-gradient(45deg, #007bff, #0056b3);
            border: none;
            color: white;
        }
        .btn-complete {
            background: linear-gradient(45deg, #28a745, #20c997);
            border: none;
            color: white;
        }
        .btn-view {
            background: linear-gradient(45deg, #6c757d, #495057);
            border: none;
            color: white;
        }
    </style>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <h4><i class="fas fa-pills me-2"></i>Pharmly</h4>
    <a href="#" class="active"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>
    <a href="/prescription/pharmacist/orders"><i class="fas fa-prescription-bottle-alt me-2"></i> Scheduled Orders</a>
    <a href="/pharmacist/inventory"><i class="fas fa-capsules me-2"></i> Medicine Inventory</a>
    <a href="/pharmacist/patients"><i class="fas fa-users me-2"></i> Patients</a>
    
    <a href="/pharmacist/login" class="text-warning mt-3"><i class="fas fa-sign-out-alt me-2"></i> Logout</a>
</div>

<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="fw-bold mb-1">Welcome back, <span th:text="${pharmacist.fullName}"></span>!</h2>
            <p class="text-muted mb-0">Manage prescriptions and pharmacy operations</p>
        </div>
        <div class="d-flex gap-2">
            <a class="btn btn-outline-primary" href="/reports/today.csv">
                <i class="fas fa-download me-2"></i>Export Report
            </a>
            <a class="btn btn-primary" href="/medicine/add">
                <i class="fas fa-plus me-2"></i>Add Medicine
            </a>
        </div>
    </div>

    <!-- Summary Cards -->
    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="summary-card">
                <div class="text-primary mb-2">
                    <i class="fas fa-prescription-bottle-alt fa-2x"></i>
                </div>
                <div class="summary-number text-primary" th:text="${pendingCount}">24</div>
                <div class="text-muted">Pending Orders</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="summary-card">
                <div class="text-success mb-2">
                    <i class="fas fa-check-circle fa-2x"></i>
                </div>
                <div class="summary-number text-success" th:text="${completedToday}">156</div>
                <div class="text-muted">Completed Today</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="summary-card">
                <div class="text-warning mb-2">
                    <i class="fas fa-exclamation-triangle fa-2x"></i>
                </div>
                <div class="summary-number text-warning" th:text="${urgentCount}">8</div>
                <div class="text-muted">Urgent Orders</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="summary-card">
                <div class="text-info mb-2">
                    <i class="fas fa-capsules fa-2x"></i>
                </div>
                <div class="summary-number text-info">1,234</div>
                <div class="text-muted">Medicines in Stock</div>
            </div>
        </div>
    </div>

    <!-- Scheduled Orders -->
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="fw-bold mb-0">
                    <i class="fas fa-clock me-2 text-primary"></i>Scheduled Orders
                </h4>
                <div class="d-flex gap-2">
                    <select class="form-select" style="width: auto;">
                        <option>All Orders</option>
                        <option>Pending</option>
                        <option>Processing</option>
                        <option>Ready</option>
                    </select>
                    <button class="btn btn-outline-secondary">
                        <i class="fas fa-filter me-1"></i>Filter
                    </button>
                </div>
            </div>

            <!-- Orders List -->
            <div th:if="${orders != null and !orders.isEmpty()}">
                <div th:each="order : ${orders}" class="order-card">
                    <div class="order-header">
                        <div class="row align-items-center">
                            <div class="col-md-8">
                                <h5 class="mb-1">
                                    <i class="fas fa-prescription-bottle-alt me-2"></i>
                                    Order #<span th:text="${order.prescriptionId}">12345</span>
                                    <span th:if="${order.isUrgent}" class="urgent-badge ms-2">
                                        <i class="fas fa-exclamation-triangle me-1"></i>URGENT
                                    </span>
                                </h5>
                                <p class="mb-0 opacity-75">
                                    <i class="fas fa-calendar-alt me-2"></i>
                                    <span th:text="${#temporals.format(order.prescriptionDate, 'dd MMMM yyyy, hh:mm a')}">15 March 2024, 10:30 AM</span>
                                </p>
                            </div>
                            <div class="col-md-4 text-end">
                                <span class="status-badge status-pending" th:text="${order.status}">Pending</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="order-body">
                        <!-- Patient Info -->
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <h6 class="text-primary mb-2">
                                    <i class="fas fa-user me-2"></i>Patient Information
                                </h6>
                                <p class="mb-1"><strong>Patient ID:</strong> <span th:text="${order.patientId}">12345</span></p>
                                <p class="mb-1"><strong>Doctor ID:</strong> <span th:text="${order.doctorId}">67890</span></p>
                            </div>
                            <div class="col-md-6">
                                <h6 class="text-success mb-2">
                                    <i class="fas fa-stethoscope me-2"></i>Diagnosis
                                </h6>
                                <p class="mb-0" th:text="${order.diagnosis}">Common cold and fever</p>
                            </div>
                        </div>

                        <!-- Prescribed Medicines -->
                        <div class="mb-3">
                            <h6 class="text-info mb-3">
                                <i class="fas fa-pills me-2"></i>Prescribed Medicines
                            </h6>
                            <div id="medicines-${order.prescriptionId}">
                                <!-- Medicines will be loaded here -->
                            </div>
                            <div class="text-center mt-2">
                                <a th:href="@{'/prescription/details/' + ${order.prescriptionId}}" 
                                   class="btn btn-sm btn-outline-info">
                                    <i class="fas fa-eye me-1"></i>View Detailed Medicines
                                </a>
                            </div>
                        </div>

                        <!-- Notes -->
                        <div class="mb-3" th:if="${order.notes != null and !order.notes.isEmpty()}">
                            <h6 class="text-warning mb-2">
                                <i class="fas fa-notes-medical me-2"></i>Special Instructions
                            </h6>
                            <p class="mb-0" th:text="${order.notes}">Take with food. Complete the full course.</p>
                        </div>

                        <!-- Action Buttons -->
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <button class="btn action-btn btn-process me-2" 
                                        th:onclick="'processOrder(' + ${order.prescriptionId} + ')'">
                                    <i class="fas fa-cogs me-1"></i>Process Order
                                </button>
                                <button class="btn action-btn btn-complete me-2" 
                                        th:onclick="'completeOrder(' + ${order.prescriptionId} + ')'">
                                    <i class="fas fa-check me-1"></i>Mark Complete
                                </button>
                                <button class="btn action-btn btn-view" 
                                        th:onclick="'viewOrder(' + ${order.prescriptionId} + ')'">
                                    <i class="fas fa-eye me-1"></i>View Details
                                </button>
                            </div>
                            <small class="text-muted">
                                <i class="fas fa-clock me-1"></i>
                                <span th:text="${#temporals.format(order.prescriptionDate, 'dd MMM, hh:mm a')}">15 Mar, 10:30 AM</span>
                            </small>
                        </div>
                    </div>
                </div>
    </div>

            <!-- Empty State -->
            <div th:if="${orders == null or orders.isEmpty()}" class="text-center py-5">
                <i class="fas fa-prescription-bottle-alt fa-4x text-muted mb-3"></i>
                <h4 class="text-muted">No Scheduled Orders</h4>
                <p class="text-muted">New prescription orders will appear here when doctors issue them.</p>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Load medicine details for each order
    document.addEventListener('DOMContentLoaded', function() {
        const orders = document.querySelectorAll('[id^="medicines-"]');
        orders.forEach(container => {
            const orderId = container.id.split('-')[1];
            loadMedicineDetails(orderId);
        });
    });

    function loadMedicineDetails(orderId) {
        const container = document.getElementById(`medicines-${orderId}`);
        fetch(`/prescription/api/medicines/${orderId}`)
            .then(r => r.json())
            .then(list => {
                if (!list || list.length === 0) {
                    container.innerHTML = '<div class="text-muted">No medicines found.</div>';
                    return;
                }
                container.innerHTML = list.map(m => `
                    <div class="medicine-item">
                        <div class="row">
                            <div class="col-md-4">
                                <strong>${m.medicineName || ''}</strong><br>
                                <small class="text-muted">${m.genericName || ''} - ${m.strength || ''}</small>
                            </div>
                            <div class="col-md-2">
                                <strong>Quantity:</strong> ${m.quantity || ''}
                            </div>
                            <div class="col-md-3">
                                <strong>Dosage:</strong> ${m.dosage || ''}, ${m.frequency || ''}
                            </div>
                            <div class="col-md-3">
                                <strong>Duration:</strong> ${m.duration || ''}
                            </div>
                        </div>
                    </div>
                `).join('');
            })
            .catch(() => {
                container.innerHTML = '<div class="text-danger">Failed to load medicines.</div>';
            });
    }

    function processOrder(orderId) {
        if (confirm('Are you sure you want to process this order?')) {
            fetch(`/prescription/update-status/${orderId}?status=Processing`, {
                method: 'POST'
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    alert('Failed to process order');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while processing the order');
            });
        }
    }

    function completeOrder(orderId) {
        if (confirm('Are you sure you want to mark this order as complete?')) {
            fetch(`/prescription/update-status/${orderId}?status=Completed`, {
                method: 'POST'
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    alert('Failed to complete order');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while completing the order');
            });
        }
    }

    function viewOrder(orderId) {
        alert(`Viewing order #${orderId} details`);
    }
</script>

</body>
</html>
