<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medicine List</title>
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
        .table thead th { background:#f1f5f9; }
        .search-box { max-width: 420px; }
        .stock-badge.low { background:#ffe4e6; color:#b91c1c; }
        .stock-badge.ok { background:#dcfce7; color:#166534; }
    </style>

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
                <h3 class="mb-1"><i class="fas fa-capsules me-2 text-primary"></i>Medicine Inventory</h3>
                <p class="text-muted mb-0">Total medicines: <span class="fw-semibold" th:text="${medicines.size()}">0</span></p>
            </div>
            <div class="input-group search-box">
                <span class="input-group-text bg-white"><i class="fas fa-search"></i></span>
                <input type="text" id="search" class="form-control" placeholder="Search by name or generic..." oninput="filterMedicines()">
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Medicine</th>
                                <th>Generic</th>
                                <th>Category</th>
                                <th>Dosage</th>
                                <th>Strength</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th>Rx</th>
                            </tr>
                        </thead>
                        <tbody id="medicine-body">
                            <tr th:each="medicine : ${medicines}">
                                <td th:text="${medicine.medicineId}"></td>
                                <td>
                                    <div class="fw-semibold" th:text="${medicine.medicineName}"></div>
                                </td>
                                <td th:text="${medicine.genericName}"></td>
                                <td><span class="badge bg-primary" th:text="${medicine.category}"></span></td>
                                <td th:text="${medicine.dosageForm}"></td>
                                <td th:text="${medicine.strength}"></td>
                                <td>$<span th:text="${medicine.unitPrice}"></span></td>
                                <td>
                                    <span th:classappend= "${medicine.stockQuantity} &lt; 10 ? 'stock-badge low badge' : 'stock-badge ok badge'" th:text="${medicine.stockQuantity}"></span>
                                </td>
                                <td>
                                    <span th:if="${medicine.isPrescriptionRequired}" class="badge bg-warning">Yes</span>
                                    <span th:unless="${medicine.isPrescriptionRequired}" class="badge bg-success">No</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script>
        function filterMedicines() {
            const q = document.getElementById('search').value.toLowerCase();
            document.querySelectorAll('#medicine-body tr').forEach(tr => {
                const text = tr.innerText.toLowerCase();
                tr.style.display = text.includes(q) ? '' : 'none';
            });
        }
    </script>
</body>
</html>


