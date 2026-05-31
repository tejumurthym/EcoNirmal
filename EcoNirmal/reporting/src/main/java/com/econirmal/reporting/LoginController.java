package com.econirmal.reporting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "EcoNirmal API is running. Go to <a href='/login'>/login</a>";
    }

    @GetMapping("/login")
    public String loginPage() {
        // ... (keep your existing login HTML code - it's long, but you already have it)
        // I'm omitting it here for brevity, but you can keep yours.
        // If you need it again, let me know.
        return "<html>...</html>"; // placeholder, keep your actual login HTML
    }

    @GetMapping("/register")
    public String registerPage() {
        // ... (keep your existing register HTML)
        return "<html>...</html>";
    }

    // ========== ADMIN DASHBOARD ==========
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Admin Dashboard</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body { background: #f4f6f9; }
                    .nav-bar { background: #2e7d32; padding: 15px; color: white; }
                    .container { margin-top: 30px; }
                    .card { margin-bottom: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                </style>
            </head>
            <body>
                <div class="nav-bar">
                    <div class="container d-flex justify-content-between">
                        <h2>🌿 EcoNirmal Admin</h2>
                        <button onclick="logout()" class="btn btn-light">Logout</button>
                    </div>
                </div>
                <div class="container">
                    <div class="card">
                        <div class="card-header bg-primary text-white">Pending Workers</div>
                        <div class="card-body" id="pendingWorkersList">Loading...</div>
                    </div>
                    <div class="card">
                        <div class="card-header bg-info text-white">All Reports</div>
                        <div class="card-body" id="reportsList">Loading...</div>
                    </div>
                </div>
                <script>
                    const user = JSON.parse(localStorage.getItem('user'));
                    if (!user || user.role !== 'ADMIN') window.location.href = '/login';
                    async function fetchWithAuth(url, opts={}) {
                        opts.headers = opts.headers || {};
                        opts.headers['Content-Type'] = 'application/json';
                        const res = await fetch(url, opts);
                        if (res.status === 401 || res.status === 403) { localStorage.removeItem('user'); window.location.href='/login'; }
                        return res;
                    }
                    async function loadPendingWorkers() {
                        const res = await fetchWithAuth('/api/admin/pending-workers');
                        const workers = await res.json();
                        const container = document.getElementById('pendingWorkersList');
                        if (workers.length === 0) { container.innerHTML = '<p>No pending workers.</p>'; return; }
                        let html = '<table class="table"><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Action</th></tr></thead><tbody>';
                        workers.forEach(w => {
                            html += `<tr><td>${w.id}</td><td>${w.name}</td><td>${w.email}</td><td><button class="btn btn-sm btn-success" onclick="approveWorker(${w.id})">Approve</button></td></tr>`;
                        });
                        html += '</tbody></table>';
                        container.innerHTML = html;
                    }
                    async function approveWorker(id) {
                        if (!confirm('Approve?')) return;
                        await fetchWithAuth(`/api/admin/approve-worker/${id}`, { method: 'PUT' });
                        loadPendingWorkers();
                    }
                    async function loadAllReports() {
                        const res = await fetchWithAuth('/api/admin/reports');
                        const reports = await res.json();
                        const container = document.getElementById('reportsList');
                        if (reports.length === 0) { container.innerHTML = '<p>No reports.</p>'; return; }
                        let html = '<div class="table-responsive"><table class="table table-striped"><thead><tr><th>ID</th><th>Citizen</th><th>Description</th><th>Location</th><th>Status</th><th>Fine</th><th>Reported</th></tr></thead><tbody>';
                        reports.forEach(r => {
                            html += `<tr><td>${r.id}</td><td>${r.citizenName}<br><small>${r.citizenEmail}</small></td><td>${r.description}</td><td>${r.latitude}, ${r.longitude}</td><td><span class="badge bg-${r.status === 'VERIFIED' ? 'success' : (r.status === 'PENDING' ? 'warning' : 'danger')}">${r.status}</span></td><td>₹${r.fineAmount || 0}</td><td>${new Date(r.reportedAt).toLocaleString()}</td></tr>`;
                        });
                        html += '</tbody></table></div>';
                        container.innerHTML = html;
                    }
                    function logout() { localStorage.removeItem('user'); window.location.href = '/login'; }
                    loadPendingWorkers();
                    loadAllReports();
                </script>
            </body>
            </html>
        """;
    }

    // ========== CITIZEN DASHBOARD ==========
    @GetMapping("/citizen-dashboard")
    public String citizenDashboard() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Citizen Dashboard</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body { background: #e9f5e9; }
                    .nav-bar { background: #2e7d32; padding: 15px; color: white; }
                    .container { margin-top: 30px; }
                    .card { margin-bottom: 20px; }
                </style>
            </head>
            <body>
                <div class="nav-bar">
                    <div class="container d-flex justify-content-between">
                        <h2>🌿 EcoNirmal Citizen</h2>
                        <button onclick="logout()" class="btn btn-light">Logout</button>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h5>Your Contribution Points</h5>
                                    <h2 id="points">0</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h5>Total Reports</h5>
                                    <h2 id="reportCount">0</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <button class="btn btn-primary" onclick="window.location.href='/submit-report'">+ New Report</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">My Reports</div>
                        <div class="card-body" id="myReportsList">Loading...</div>
                    </div>
                </div>
                <script>
                    const user = JSON.parse(localStorage.getItem('user'));
                    if (!user || user.role !== 'CITIZEN') window.location.href = '/login';
                    document.getElementById('points').innerText = user.contributionPoints || 0;
                    async function loadMyReports() {
                        const res = await fetch('/api/reports/my', { headers: { 'Authorization': 'Basic ' + btoa(user.email + ':123456') } });
                        const reports = await res.json();
                        const container = document.getElementById('myReportsList');
                        if (reports.length === 0) { container.innerHTML = '<p>No reports yet.</p>'; return; }
                        let html = '<table class="table"><thead><tr><th>ID</th><th>Description</th><th>Status</th><th>Fine</th><th>Reported</th></tr></thead><tbody>';
                        reports.forEach(r => {
                            html += `<tr><td>${r.id}</td><td>${r.description}</td><td><span class="badge bg-${r.status === 'VERIFIED' ? 'success' : (r.status === 'PENDING' ? 'warning' : 'danger')}">${r.status}</span></td><td>₹${r.fineAmount || 0}</td><td>${new Date(r.reportedAt).toLocaleString()}</td></tr>`;
                        });
                        html += '</tbody></table>';
                        container.innerHTML = html;
                        document.getElementById('reportCount').innerText = reports.length;
                    }
                    function logout() { localStorage.removeItem('user'); window.location.href = '/login'; }
                    loadMyReports();
                </script>
            </body>
            </html>
        """;
    }

    // ========== WORKER DASHBOARD ==========
    @GetMapping("/worker-dashboard")
    public String workerDashboard() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Worker Dashboard</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body { background: #f0f2f5; }
                    .nav-bar { background: #2e7d32; padding: 15px; color: white; }
                    .container { margin-top: 30px; }
                </style>
            </head>
            <body>
                <div class="nav-bar">
                    <div class="container d-flex justify-content-between">
                        <h2>🌿 EcoNirmal Worker</h2>
                        <button onclick="logout()" class="btn btn-light">Logout</button>
                    </div>
                </div>
                <div class="container">
                    <div class="card">
                        <div class="card-header bg-warning">Pending Reports</div>
                        <div class="card-body" id="pendingReportsList">Loading...</div>
                    </div>
                </div>
                <script>
                    const user = JSON.parse(localStorage.getItem('user'));
                    if (!user || user.role !== 'WORKER') window.location.href = '/login';
                    async function loadPendingReports() {
                        const res = await fetch('/api/worker/reports/pending', { headers: { 'Authorization': 'Basic ' + btoa(user.email + ':123456') } });
                        const reports = await res.json();
                        const container = document.getElementById('pendingReportsList');
                        if (reports.length === 0) { container.innerHTML = '<p>No pending reports.</p>'; return; }
                        let html = '<div class="row">';
                        reports.forEach(r => {
                            html += `<div class="col-md-6 mb-3"><div class="card"><div class="card-body"><h5>Report #${r.id}</h5><p><strong>Description:</strong> ${r.description}</p><p><strong>Location:</strong> ${r.latitude}, ${r.longitude}</p><p><strong>Image:</strong> <a href="/uploads/${r.imagePath}" target="_blank">View</a></p><button class="btn btn-sm btn-success" onclick="verifyReport(${r.id})">Verify</button> <button class="btn btn-sm btn-danger" onclick="rejectReport(${r.id})">Reject</button></div></div></div>`;
                        });
                        html += '</div>';
                        container.innerHTML = html;
                    }
                    async function verifyReport(id) {
                        const fine = prompt('Enter fine amount:');
                        if (!fine) return;
                        const violator = prompt('Violator name:');
                        const comment = prompt('Worker comment:');
                        const body = JSON.stringify({ violatorName: violator, fineAmount: parseFloat(fine), workerComment: comment });
                        const res = await fetch(`/api/worker/reports/${id}/verify`, { method: 'PUT', headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa(user.email + ':123456') }, body });
                        const result = await res.text();
                        alert(result);
                        loadPendingReports();
                    }
                    async function rejectReport(id) {
                        const comment = prompt('Reason for rejection:');
                        if (!comment) return;
                        const body = JSON.stringify({ workerComment: comment });
                        const res = await fetch(`/api/worker/reports/${id}/reject`, { method: 'PUT', headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa(user.email + ':123456') }, body });
                        const result = await res.text();
                        alert(result);
                        loadPendingReports();
                    }
                    function logout() { localStorage.removeItem('user'); window.location.href = '/login'; }
                    loadPendingReports();
                </script>
            </body>
            </html>
        """;
    }
}