// API Configuration
const API_BASE_URL = '/api/v1/tasks';

// Application State
let tasks = [];
let currentEditingTaskId = null;
let taskToDelete = null;

// DOM Elements
const taskList = document.getElementById('taskList');
const loadingState = document.getElementById('loadingState');
const emptyState = document.getElementById('emptyState');
const errorMessage = document.getElementById('errorMessage');
const taskModal = document.getElementById('taskModal');
const deleteModal = document.getElementById('deleteModal');
const taskForm = document.getElementById('taskForm');
const statusFilter = document.getElementById('statusFilter');
const priorityFilter = document.getElementById('priorityFilter');

// Form Elements
const modalTitle = document.getElementById('modalTitle');
const submitBtnText = document.getElementById('submitBtnText');
const taskTitleInput = document.getElementById('taskTitle');
const taskDescriptionInput = document.getElementById('taskDescription');
const taskStartDateInput = document.getElementById('taskStartDate');
const taskEndDateInput = document.getElementById('taskEndDate');
const taskStatusInput = document.getElementById('taskStatus');
const taskPriorityInput = document.getElementById('taskPriority');
const charCount = document.getElementById('charCount');

// Initialize Application
document.addEventListener('DOMContentLoaded', () => {
    initializeEventListeners();
    loadTasks();
});

// Event Listeners
function initializeEventListeners() {
    // Add Task Button
    document.getElementById('addTaskBtn').addEventListener('click', openAddTaskModal);

    // Modal Close Buttons
    document.getElementById('closeModal').addEventListener('click', closeTaskModal);
    document.getElementById('cancelBtn').addEventListener('click', closeTaskModal);
    document.getElementById('closeDeleteModal').addEventListener('click', closeDeleteModal);
    document.getElementById('cancelDeleteBtn').addEventListener('click', closeDeleteModal);

    // Form Submit
    taskForm.addEventListener('submit', handleFormSubmit);

    // Delete Confirmation
    document.getElementById('confirmDeleteBtn').addEventListener('click', confirmDelete);

    // Filters
    statusFilter.addEventListener('change', filterTasks);
    priorityFilter.addEventListener('change', filterTasks);

    // Character Count
    taskDescriptionInput.addEventListener('input', updateCharCount);

    // Close modals on backdrop click
    taskModal.addEventListener('click', (e) => {
        if (e.target === taskModal) closeTaskModal();
    });
    deleteModal.addEventListener('click', (e) => {
        if (e.target === deleteModal) closeDeleteModal();
    });

    // Keyboard shortcuts
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            closeTaskModal();
            closeDeleteModal();
        }
    });
}

// API Functions
async function fetchTasks() {
    const response = await fetch(API_BASE_URL);
    if (!response.ok) {
        throw new Error('Failed to fetch tasks');
    }
    return await response.json();
}

async function createTask(taskData) {
    const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(taskData),
    });
    if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to create task');
    }
    return await response.json();
}

async function updateTask(taskId, taskData) {
    const response = await fetch(`${API_BASE_URL}/${taskId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(taskData),
    });
    if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to update task');
    }
    return await response.json();
}

async function closeTask(taskId) {
    const response = await fetch(`${API_BASE_URL}/${taskId}/close`, {
        method: 'PATCH',
    });
    if (!response.ok) {
        throw new Error('Failed to close task');
    }
    return await response.json();
}

async function deleteTask(taskId) {
    const response = await fetch(`${API_BASE_URL}/${taskId}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete task');
    }
}

// Load and Display Tasks
async function loadTasks() {
    showLoading();
    hideError();

    try {
        tasks = await fetchTasks();
        displayTasks(tasks);
    } catch (error) {
        showError('Failed to load tasks. Please try again later.');
        console.error('Error loading tasks:', error);
    } finally {
        hideLoading();
    }
}

function displayTasks(tasksToDisplay) {
    taskList.innerHTML = '';

    if (tasksToDisplay.length === 0) {
        showEmptyState();
        return;
    }

    hideEmptyState();

    tasksToDisplay.forEach(task => {
        const taskCard = createTaskCard(task);
        taskList.appendChild(taskCard);
    });
}

function createTaskCard(task) {
    const card = document.createElement('div');
    card.className = 'task-card';
    card.innerHTML = `
        <div class="task-card-header">
            <h3 class="task-title">${escapeHtml(task.taskTitle)}</h3>
            <div class="task-badges">
                <span class="badge badge-status-${task.taskStatus}">${formatStatus(task.taskStatus)}</span>
                <span class="badge badge-priority-${task.taskPriority}">${task.taskPriority}</span>
            </div>
        </div>

        ${task.taskDescription ? `<p class="task-description">${escapeHtml(task.taskDescription)}</p>` : ''}

        <div class="task-dates">
            ${task.taskStartDate ? `
                <div class="task-date">
                    <strong>Start:</strong> ${formatDate(task.taskStartDate)}
                </div>
            ` : ''}
            ${task.taskEndDate ? `
                <div class="task-date">
                    <strong>End:</strong> ${formatDate(task.taskEndDate)}
                </div>
            ` : ''}
            <div class="task-date">
                <strong>Created:</strong> ${formatDateTime(task.taskCreated)}
            </div>
        </div>

        <div class="task-actions">
            ${task.taskStatus !== 'CLOSED' ? `
                <button class="btn btn-primary btn-sm" onclick="handleCloseTask('${task.taskId}')">
                    <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                        <path d="M2 7L5.5 10.5L12 3.5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    Mark as Closed
                </button>
            ` : ''}
            <button class="btn btn-secondary btn-sm" onclick="openEditTaskModal('${task.taskId}')">
                <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                    <path d="M10 1L13 4L5 12H2V9L10 1Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                Edit
            </button>
            <button class="btn btn-danger btn-sm" onclick="openDeleteModal('${task.taskId}')">
                <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                    <path d="M1 3.5H13M5.5 6.5V10.5M8.5 6.5V10.5M2 3.5L3 12.5H11L12 3.5M5.5 3.5V1.5H8.5V3.5" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                Delete
            </button>
        </div>
    `;

    return card;
}

// Modal Functions
function openAddTaskModal() {
    currentEditingTaskId = null;
    modalTitle.textContent = 'Add New Task';
    submitBtnText.textContent = 'Create Task';
    taskForm.reset();
    charCount.textContent = '0';
    taskModal.classList.remove('hidden');
}

function openEditTaskModal(taskId) {
    const task = tasks.find(t => t.taskId === taskId);
    if (!task) return;

    currentEditingTaskId = taskId;
    modalTitle.textContent = 'Edit Task';
    submitBtnText.textContent = 'Update Task';

    taskTitleInput.value = task.taskTitle;
    taskDescriptionInput.value = task.taskDescription || '';
    taskStartDateInput.value = task.taskStartDate || '';
    taskEndDateInput.value = task.taskEndDate || '';
    taskStatusInput.value = task.taskStatus;
    taskPriorityInput.value = task.taskPriority;

    updateCharCount();
    taskModal.classList.remove('hidden');
}

function closeTaskModal() {
    taskModal.classList.add('hidden');
    currentEditingTaskId = null;
    taskForm.reset();
}

function openDeleteModal(taskId) {
    taskToDelete = taskId;
    deleteModal.classList.remove('hidden');
}

function closeDeleteModal() {
    deleteModal.classList.add('hidden');
    taskToDelete = null;
}

// Form Handling
async function handleFormSubmit(e) {
    e.preventDefault();

    const taskData = {
        taskTitle: taskTitleInput.value.trim(),
        taskDescription: taskDescriptionInput.value.trim() || null,
        taskStartDate: taskStartDateInput.value || null,
        taskEndDate: taskEndDateInput.value || null,
        taskStatus: taskStatusInput.value,
        taskPriority: taskPriorityInput.value,
    };

    // Validate dates
    if (taskData.taskStartDate && taskData.taskEndDate) {
        if (new Date(taskData.taskStartDate) > new Date(taskData.taskEndDate)) {
            showError('End date must be after start date');
            return;
        }
    }

    const submitBtn = document.getElementById('submitBtn');
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<span>Saving...</span>';

    try {
        if (currentEditingTaskId) {
            await updateTask(currentEditingTaskId, taskData);
        } else {
            await createTask(taskData);
        }

        closeTaskModal();
        await loadTasks();
    } catch (error) {
        showError(error.message);
        console.error('Error saving task:', error);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = `<span id="submitBtnText">${currentEditingTaskId ? 'Update Task' : 'Create Task'}</span>`;
    }
}

// Task Actions
async function handleCloseTask(taskId) {
    try {
        await closeTask(taskId);
        await loadTasks();
    } catch (error) {
        showError('Failed to close task. Please try again.');
        console.error('Error closing task:', error);
    }
}

async function confirmDelete() {
    if (!taskToDelete) return;

    const confirmBtn = document.getElementById('confirmDeleteBtn');
    confirmBtn.disabled = true;
    confirmBtn.textContent = 'Deleting...';

    try {
        await deleteTask(taskToDelete);
        closeDeleteModal();
        await loadTasks();
    } catch (error) {
        showError('Failed to delete task. Please try again.');
        console.error('Error deleting task:', error);
    } finally {
        confirmBtn.disabled = false;
        confirmBtn.textContent = 'Delete';
    }
}

// Filtering
function filterTasks() {
    const statusValue = statusFilter.value;
    const priorityValue = priorityFilter.value;

    let filteredTasks = tasks;

    if (statusValue !== 'all') {
        filteredTasks = filteredTasks.filter(task => task.taskStatus === statusValue);
    }

    if (priorityValue !== 'all') {
        filteredTasks = filteredTasks.filter(task => task.taskPriority === priorityValue);
    }

    displayTasks(filteredTasks);
}

// UI Helper Functions
function showLoading() {
    loadingState.classList.remove('hidden');
    taskList.classList.add('hidden');
    emptyState.classList.add('hidden');
}

function hideLoading() {
    loadingState.classList.add('hidden');
    taskList.classList.remove('hidden');
}

function showEmptyState() {
    emptyState.classList.remove('hidden');
    taskList.classList.add('hidden');
}

function hideEmptyState() {
    emptyState.classList.add('hidden');
    taskList.classList.remove('hidden');
}

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.classList.remove('hidden');

    setTimeout(() => {
        hideError();
    }, 5000);
}

function hideError() {
    errorMessage.classList.add('hidden');
}

function updateCharCount() {
    const count = taskDescriptionInput.value.length;
    charCount.textContent = count;

    if (count > 450) {
        charCount.style.color = 'var(--danger-color)';
    } else {
        charCount.style.color = 'var(--gray-400)';
    }
}

// Utility Functions
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function formatStatus(status) {
    switch(status) {
        case 'OPEN':
            return 'Open';
        case 'IN_ROGRESS':
            return 'In Progress';
        case 'CLOSED':
            return 'Closed';
        default:
            return status;
    }
}

// Make functions globally accessible for onclick handlers
window.openEditTaskModal = openEditTaskModal;
window.openDeleteModal = openDeleteModal;
window.handleCloseTask = handleCloseTask;
