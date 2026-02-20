async function deleteMedicine(id) {
    const confirmed = window.confirm('Are you sure you want to delete this medicine?');
    if (!confirmed) return;

    const response = await fetch(`/api/medicines/${id}`, { method: 'DELETE' });
    if (response.ok) {
        showMessage('Medicine deleted successfully.', 'success');
        setTimeout(() => window.location.reload(), 500);
    } else {
        showMessage('Failed to delete medicine.', 'error');
    }
}

async function updateStock(id) {
    const newStock = prompt('Enter new stock quantity:');
    if (newStock === null) return;

    const stock = Number(newStock);
    if (Number.isNaN(stock) || stock < 0) {
        showMessage('Please enter a valid non-negative stock value.', 'error');
        return;
    }

    const response = await fetch(`/api/medicines/${id}/stock`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ stockQuantity: stock })
    });

    if (response.ok) {
        showMessage('Stock updated successfully.', 'success');
        setTimeout(() => window.location.reload(), 500);
    } else {
        showMessage('Failed to update stock.', 'error');
    }
}

async function filterMedicines(filterType) {
    if (filterType === 'all') {
        window.location.href = '/medicines';
        return;
    }

    const response = await fetch(`/api/medicines/filter/${filterType}`);
    if (!response.ok) {
        showMessage('Failed to load filtered medicines.', 'error');
        return;
    }

    const medicines = await response.json();
    const tbody = document.getElementById('medicineTableBody');
    tbody.innerHTML = '';

    medicines.forEach(m => {
        const row = document.createElement('tr');
        const isExpired = new Date(m.expiryDate) < new Date(new Date().toISOString().split('T')[0]);
        const isLow = m.stockQuantity > 0 && m.stockQuantity <= m.lowStockThreshold;
        if (isExpired) row.classList.add('row-expired');
        else if (isLow) row.classList.add('row-low');

        row.innerHTML = `
            <td>${m.name}</td>
            <td>${m.companyName}</td>
            <td>${m.type}</td>
            <td>${m.price}</td>
            <td>${m.stockQuantity}</td>
            <td>${m.expiryDate}</td>
            <td>${m.medicineClass}</td>
            <td>${m.shelfLocation}</td>
            <td class="actions-row">
                <a class="btn tiny" href="/medicines/${m.id}/edit">Edit</a>
                <button class="btn tiny warning" onclick="updateStock(${m.id})">Update Stock</button>
                <button class="btn tiny danger" onclick="deleteMedicine(${m.id})">Delete</button>
            </td>`;
        tbody.appendChild(row);
    });

    showMessage(`Showing ${filterType.replace('-', ' ')} medicines (${medicines.length}).`, 'success');
}

function showMessage(message, type) {
    const box = document.getElementById('messageBox');
    box.innerHTML = `<div class="alert ${type}">${message}</div>`;
}
