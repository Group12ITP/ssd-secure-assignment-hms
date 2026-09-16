<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Patients with Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<style>
    .sidebar { width: 260px; height: 100vh; position: fixed; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #fff; padding-top: 24px; }
    .sidebar h4 { text-align: center; margin-bottom: 20px; }
    .sidebar a { display:block; padding:12px 22px; color: rgba(255,255,255,0.85); text-decoration:none; border-left: 4px solid transparent; }
    .sidebar a:hover, .sidebar a.active { background: rgba(255,255,255,0.1); color: #fff; border-left-color: #fff; }
    .content { margin-left: 260px; padding: 24px; }
    .card { border-radius: 16px; box-shadow: 0 6px 20px rgba(0,0,0,0.08); }
    .search-box { max-width: 380px; }
    .badge-pill { border-radius: 999px; padding: 6px 12px; }
    .table thead th { background:#f1f5f9; }
    .table-hover tbody tr:hover { background: #f8fafc; }
    .empty { text-align: center; color: #64748b; padding: 40px 0; }
    .empty i { color:#cbd5e1; }
    .patient-card { border-radius: 16px; box-shadow: 0 6px 20px rgba(0,0,0,0.08); overflow:hidden; }
    .patient-card .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color:#fff; padding:16px 20px; }
    .patient-card .body { padding: 16px 20px; background:#fff; }
    .avatar { width:44px;height:44px;border-radius:50%;background: rgba(255,255,255,0.2); display:flex;align-items:center;justify-content:center; font-weight:700; }
    .muted { color:#64748b; }
</style>

<div class="sidebar">
    <h4><i class="fas fa-pills me-2"></i>Pharmly</h4>
    <a href="/pharmacist/dashboard"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>
    <a href="/prescription/pharmacist/orders"><i class="fas fa-prescription-bottle-alt me-2"></i> Scheduled Orders</a>
    <a href="/pharmacist/inventory"><i class="fas fa-capsules me-2"></i> Medicine Inventory</a>
    <a href="/pharmacist/patients" class="active"><i class="fas fa-users me-2"></i> Patients</a>
    <a href="/pharmacist/login" class="text-warning mt-2"><i class="fas fa-sign-out-alt me-2"></i> Logout</a>
</div>

<div class="content">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <h3 class="mb-1"><i class="fas fa-users me-2 text-primary"></i>Patients with Orders</h3>
            <p class="text-muted mb-0">Completed orders grouped by patient</p>
        </div>
        <div class="input-group search-box">
            <span class="input-group-text bg-white"><i class="fas fa-search"></i></span>
            <input type="text" id="search" class="form-control" placeholder="Search by patient id..." oninput="filterPatients()">
        </div>
    </div>

    <div class="row g-4" id="patients-body">
        <div class="col-12 col-md-6 col-lg-4" th:each="card : ${patientCards}">
            <div class="patient-card">
                <div class="header d-flex align-items-center justify-content-between">
                    <div class="d-flex align-items-center gap-2">
                        <div class="avatar" th:text="${#strings.substring((card.patient != null ? card.patient.fullName : 'P'),0,1)}">P</div>
                        <div>
                            <div class="fw-semibold" th:text="${card.patient != null ? card.patient.fullName : 'Unknown Patient'}">John Doe</div>
                            <div class="small" th:text="${card.patient != null ? card.patient.nicPassport : ''}">NIC</div>
                        </div>
                    </div>
                    <span class="badge bg-light text-dark" th:text="${card.prescriptionCount + ' orders'}">2 orders</span>
                </div>
                <div class="body">
                    <div class="mb-2">
                        <div class="muted small mb-1">Latest Prescription</div>
                        <div>
                            <i class="fas fa-file-medical me-1 text-primary"></i>
                            <a th:href="@{'/prescription/details/' + ${card.latestPrescription.prescriptionId}}" th:text="${'Prescription #' + card.latestPrescription.prescriptionId}">Prescription #1</a>
                            <span class="muted ms-2" th:text="${#temporals.format(card.latestPrescription.prescriptionDate, 'dd MMM yyyy')} ">12 Mar 2024</span>
                        </div>
                        <div class="muted" th:text="${card.latestPrescription.diagnosis}">Diagnosis</div>
                    </div>
                    <div class="mb-2" th:if="${card.appointment != null}">
                        <div class="muted small mb-1">Appointment</div>
                        <div><i class="fas fa-calendar-alt me-1 text-success"></i><span th:text="${#temporals.format(card.appointment.appointmentDate, 'dd MMM yyyy, hh:mm a')}">10 Mar 2024, 10:30 AM</span></div>
                        <div class="muted"><i class="fas fa-info-circle me-1"></i><span th:text="${card.appointment.status}">Completed</span></div>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                        <a class="btn btn-sm btn-primary" th:href="@{'/prescription/patient/' + ${card.patient != null ? card.patient.patientId : 0}}">
                            <i class="fas fa-file-medical me-1"></i>View All Prescriptions
                        </a>
                        <span class="muted small">
                            <i class="fas fa-phone me-1"></i>
                            <span th:text="${card.patient != null ? card.patient.contactNumber : ''}">+1 000 000</span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="empty" th:if="${patientCards == null or #lists.isEmpty(patientCards)}">
        <i class="fas fa-users fa-3x mb-3"></i>
        <div>No patients found.</div>
    </div>
</div>

<script>
    function filterPatients() {
        const q = document.getElementById('search').value.toLowerCase();
        document.querySelectorAll('#patients-body tr').forEach(tr => {
            const text = tr.innerText.toLowerCase();
            tr.style.display = text.includes(q) ? '' : 'none';
        });
    }
</script>
</body>
</html>




