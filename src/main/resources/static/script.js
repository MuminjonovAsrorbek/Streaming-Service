// Global Variables
const API_BASE_URL = '/api';
let currentPage = 0;
let currentContentId = null;
let currentUserId = null;

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    showSection('home');
    loadContent();
    loadUsers();
});

// Navigation Functions
function showSection(sectionId) {
    // Hide all sections
    const sections = document.querySelectorAll('section');
    sections.forEach(section => {
        section.classList.remove('active');
    });
    
    // Show selected section
    const targetSection = document.getElementById(sectionId);
    if (targetSection) {
        targetSection.classList.add('active');
    }
    
    // Update navigation links
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${sectionId}`) {
            link.classList.add('active');
        }
    });
    
    // Load section-specific data
    switch(sectionId) {
        case 'content':
            loadContent();
            break;
        case 'users':
            loadUsers();
            break;
        case 'admin':
            // Admin section is static
            break;
    }
}

// Add event listeners for navigation
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        const sectionId = this.getAttribute('href').substring(1);
        showSection(sectionId);
    });
});

// API Functions
async function apiRequest(url, options = {}) {
    showLoadingSpinner(true);
    try {
        const response = await fetch(API_BASE_URL + url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        console.error('API Request Error:', error);
        showToast('Xatolik yuz berdi: ' + error.message, 'error');
        throw error;
    } finally {
        showLoadingSpinner(false);
    }
}

// Content Functions
async function loadContent(page = 0) {
    try {
        const data = await apiRequest(`/content?page=${page}`);
        displayContent(data);
        updatePagination(data);
    } catch (error) {
        console.error('Error loading content:', error);
    }
}

function displayContent(data) {
    const contentGrid = document.getElementById('contentGrid');
    
    if (!data.content || data.content.length === 0) {
        contentGrid.innerHTML = '<div class="no-content">Hech qanday kontent topilmadi</div>';
        return;
    }
    
    contentGrid.innerHTML = data.content.map(content => `
        <div class="content-card" onclick="showContentDetail(${content.id})">
            <div class="content-poster">
                <i class="fas fa-${getContentIcon(content.contentType)}"></i>
            </div>
            <div class="content-info">
                <h3>${content.name}</h3>
                <div class="content-meta">
                    <span class="meta-item">${content.contentType}</span>
                    <span class="meta-item">${content.publishedYear}</span>
                    <span class="meta-item">${content.duration} daqiqa</span>
                    <span class="meta-item">${content.ageLimit}</span>
                    <span class="meta-item ${content.premiumStatus.toLowerCase()}">${content.premiumStatus}</span>
                </div>
                <div class="content-description">
                    ${content.description || 'Tavsif mavjud emas'}
                </div>
                <div class="content-actions">
                    <button class="btn-primary btn-small" onclick="event.stopPropagation(); watchContent(${content.id})">
                        <i class="fas fa-play"></i> Tomosha qilish
                    </button>
                    <button class="btn-secondary btn-small" onclick="event.stopPropagation(); showRateModal(${content.id})">
                        <i class="fas fa-star"></i> Baholash
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}

function getContentIcon(contentType) {
    switch(contentType) {
        case 'FILM': return 'film';
        case 'SERIAL': return 'tv';
        case 'CARTOON': return 'child';
        case 'ANIME': return 'dragon';
        default: return 'video';
    }
}

async function showContentDetail(contentId) {
    try {
        const content = await apiRequest(`/content/${contentId}`);
        currentContentId = contentId;
        
        const avgRating = await apiRequest(`/content/${contentId}/rating`);
        
        document.getElementById('contentDetailTitle').textContent = content.name;
        document.getElementById('contentDetailBody').innerHTML = `
            <div class="content-detail">
                <div class="content-poster-large">
                    <i class="fas fa-${getContentIcon(content.contentType)}"></i>
                </div>
                <div class="content-details">
                    <h2>${content.name}</h2>
                    <div class="detail-row">
                        <span class="detail-label">Tavsif:</span>
                        <span class="detail-value">${content.description || 'Tavsif mavjud emas'}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Yil:</span>
                        <span class="detail-value">${content.publishedYear}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Davomiyligi:</span>
                        <span class="detail-value">${content.duration} daqiqa</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Turi:</span>
                        <span class="detail-value">${content.contentType}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Yosh chegarasi:</span>
                        <span class="detail-value">${content.ageLimit}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Premium holati:</span>
                        <span class="detail-value">${content.premiumStatus}</span>
                    </div>
                    <div class="rating-info">
                        <div class="detail-row">
                            <span class="detail-label">O'rtacha baho:</span>
                            <span class="detail-value">${avgRating ? avgRating.toFixed(1) : 'Baholanmagan'}/10</span>
                        </div>
                    </div>
                </div>
            </div>
            ${content.genres && content.genres.length > 0 ? `
                <div class="detail-row">
                    <span class="detail-label">Janrlar:</span>
                    <div class="genres-list">
                        ${content.genres.map(genre => `<span class="genre-tag">${genre.name}</span>`).join('')}
                    </div>
                </div>
            ` : ''}
            ${content.actors && content.actors.length > 0 ? `
                <div class="detail-row">
                    <span class="detail-label">Aktyorlar:</span>
                    <div class="actors-list">
                        ${content.actors.map(actor => `<span class="actor-tag">${actor.fullName}</span>`).join('')}
                    </div>
                </div>
            ` : ''}
        `;
        
        showModal('contentDetailModal');
    } catch (error) {
        console.error('Error loading content detail:', error);
    }
}

async function filterContent() {
    const contentType = document.getElementById('contentTypeFilter').value;
    const ageLimit = document.getElementById('ageLimitFilter').value;
    const premiumStatus = document.getElementById('premiumFilter').value;
    
    try {
        const params = new URLSearchParams();
        if (contentType) params.append('contentType', contentType);
        if (ageLimit) params.append('ageLimit', ageLimit);
        if (premiumStatus) params.append('premiumStatus', premiumStatus);
        params.append('page', '0');
        params.append('size', '10');
        
        const data = await apiRequest(`/content/filter?${params.toString()}`);
        displayContent(data);
        updatePagination(data);
        currentPage = 0;
    } catch (error) {
        console.error('Error filtering content:', error);
    }
}

async function createContent(event) {
    event.preventDefault();
    
    const contentData = {
        name: document.getElementById('contentName').value,
        description: document.getElementById('contentDescription').value,
        publishedYear: parseInt(document.getElementById('contentYear').value),
        duration: parseInt(document.getElementById('contentDuration').value),
        contentType: document.getElementById('contentType').value,
        ageLimit: document.getElementById('contentAgeLimit').value,
        premiumStatus: document.getElementById('contentPremium').value
    };
    
    try {
        await apiRequest('/content', {
            method: 'POST',
            body: JSON.stringify(contentData)
        });
        
        showToast('Kontent muvaffaqiyatli qo\'shildi!', 'success');
        closeModal('addContentModal');
        document.getElementById('addContentForm').reset();
        loadContent();
    } catch (error) {
        console.error('Error creating content:', error);
    }
}

async function rateContent(event) {
    event.preventDefault();
    
    const ratingData = {
        userId: parseInt(document.getElementById('userId').value),
        rating: parseInt(document.getElementById('rating').value),
        comment: document.getElementById('ratingComment').value
    };
    
    try {
        await apiRequest(`/content/${currentContentId}/rate`, {
            method: 'POST',
            body: JSON.stringify(ratingData)
        });
        
        showToast('Kontent muvaffaqiyatli baholandi!', 'success');
        closeModal('rateModal');
        document.getElementById('rateForm').reset();
    } catch (error) {
        console.error('Error rating content:', error);
    }
}

// User Functions
async function loadUsers(page = 0) {
    try {
        const data = await apiRequest(`/user?page=${page}`);
        displayUsers(data);
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function displayUsers(data) {
    const usersGrid = document.getElementById('usersGrid');
    
    if (!data.content || data.content.length === 0) {
        usersGrid.innerHTML = '<div class="no-content">Hech qanday foydalanuvchi topilmadi</div>';
        return;
    }
    
    usersGrid.innerHTML = data.content.map(user => `
        <div class="user-card" onclick="showUserDetail(${user.id})">
            <div class="user-avatar">
                <i class="fas fa-user"></i>
            </div>
            <div class="user-info">
                <h3>${user.fullName}</h3>
                <p><i class="fas fa-envelope"></i> ${user.email}</p>
                <p><i class="fas fa-id-card"></i> ID: ${user.id}</p>
                ${user.subscriptionId ? 
                    `<span class="user-status status-active">Obuna mavjud</span>` : 
                    `<span class="user-status status-inactive">Obuna yo'q</span>`
                }
            </div>
        </div>
    `).join('');
}

async function showUserDetail(userId) {
    try {
        const user = await apiRequest(`/user/${userId}`);
        currentUserId = userId;
        
        let subscriptionInfo = '';
        if (user.subscriptionId) {
            try {
                const subscription = await apiRequest(`/user/${userId}/subscription`);
                subscriptionInfo = `
                    <div class="subscription-info">
                        <h4>Obuna ma'lumotlari</h4>
                        <p>Turi: ${subscription.subscriptionType}</p>
                        <p>Holati: ${subscription.status}</p>
                        <p>Boshlanish sanasi: ${new Date(subscription.startDate).toLocaleDateString()}</p>
                        <p>Tugash sanasi: ${new Date(subscription.endDate).toLocaleDateString()}</p>
                    </div>
                `;
            } catch (error) {
                subscriptionInfo = '<div class="subscription-info">Obuna ma\'lumotlarini yuklashda xatolik</div>';
            }
        }
        
        document.getElementById('userDetailTitle').textContent = user.fullName;
        document.getElementById('userDetailBody').innerHTML = `
            <div class="user-detail">
                <div class="detail-row">
                    <span class="detail-label">To'liq ism:</span>
                    <span class="detail-value">${user.fullName}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email:</span>
                    <span class="detail-value">${user.email}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">ID:</span>
                    <span class="detail-value">${user.id}</span>
                </div>
                ${subscriptionInfo}
            </div>
        `;
        
        showModal('userDetailModal');
    } catch (error) {
        console.error('Error loading user detail:', error);
    }
}

async function createUser(event) {
    event.preventDefault();
    
    const userData = {
        fullName: document.getElementById('registerFullName').value,
        email: document.getElementById('registerEmail').value,
        password: document.getElementById('registerPassword').value
    };
    
    try {
        await apiRequest('/user', {
            method: 'POST',
            body: JSON.stringify(userData)
        });
        
        showToast('Foydalanuvchi muvaffaqiyatli ro\'yxatdan o\'tdi!', 'success');
        closeModal('registerModal');
        document.getElementById('registerForm').reset();
        loadUsers();
    } catch (error) {
        console.error('Error creating user:', error);
    }
}

async function createUserFromAdmin(event) {
    event.preventDefault();
    
    const userData = {
        fullName: document.getElementById('userFullName').value,
        email: document.getElementById('userEmail').value,
        password: document.getElementById('userPassword').value
    };
    
    try {
        await apiRequest('/user', {
            method: 'POST',
            body: JSON.stringify(userData)
        });
        
        showToast('Foydalanuvchi muvaffaqiyatli qo\'shildi!', 'success');
        closeModal('addUserModal');
        document.getElementById('addUserForm').reset();
        loadUsers();
    } catch (error) {
        console.error('Error creating user:', error);
    }
}

async function buySubscription(event) {
    event.preventDefault();
    
    const subscriptionData = {
        subscriptionType: document.getElementById('subscriptionType').value,
        durationMonths: parseInt(document.getElementById('subscriptionDuration').value)
    };
    
    try {
        await apiRequest(`/user/${currentUserId}/subscribe`, {
            method: 'POST',
            body: JSON.stringify(subscriptionData)
        });
        
        showToast('Obuna muvaffaqiyatli sotib olindi!', 'success');
        closeModal('subscriptionModal');
        document.getElementById('subscriptionForm').reset();
        showUserDetail(currentUserId); // Refresh user details
    } catch (error) {
        console.error('Error buying subscription:', error);
    }
}

async function showUserHistory() {
    try {
        const history = await apiRequest(`/user/${currentUserId}/history`);
        
        const historyHtml = history.length > 0 ? `
            <div class="history-list">
                ${history.map(item => `
                    <div class="history-item">
                        <div>
                            <strong>${item.contentName || 'Noma\'lum kontent'}</strong>
                            <br>
                            <small>Tomosha qilingan: ${new Date(item.watchedAt).toLocaleDateString()}</small>
                        </div>
                        <div>
                            <span class="detail-value">${item.watchedDuration || 0} daqiqa</span>
                        </div>
                    </div>
                `).join('')}
            </div>
        ` : '<p>Tomosha tarixi mavjud emas</p>';
        
        document.getElementById('userDetailBody').innerHTML += `
            <div class="user-history">
                <h4>Tomosha Tarixi</h4>
                ${historyHtml}
            </div>
        `;
    } catch (error) {
        console.error('Error loading user history:', error);
        showToast('Tomosha tarixini yuklashda xatolik', 'error');
    }
}

// Admin Functions
async function createActor(event) {
    event.preventDefault();
    
    const actorData = {
        fullName: document.getElementById('actorFullName').value
    };
    
    try {
        await apiRequest('/actor', {
            method: 'POST',
            body: JSON.stringify(actorData)
        });
        
        showToast('Aktor muvaffaqiyatli qo\'shildi!', 'success');
        closeModal('addActorModal');
        document.getElementById('addActorForm').reset();
    } catch (error) {
        console.error('Error creating actor:', error);
    }
}

async function createGenre(event) {
    event.preventDefault();
    
    const genreData = {
        name: document.getElementById('genreName').value
    };
    
    try {
        await apiRequest('/genre', {
            method: 'POST',
            body: JSON.stringify(genreData)
        });
        
        showToast('Janr muvaffaqiyatli qo\'shildi!', 'success');
        closeModal('addGenreModal');
        document.getElementById('addGenreForm').reset();
    } catch (error) {
        console.error('Error creating genre:', error);
    }
}

async function watchContent(contentId) {
    // This would open a video player or redirect to streaming page
    // For now, we'll just simulate watching
    const userId = prompt('Foydalanuvchi ID kiriting:');
    if (!userId) return;
    
    const watchData = {
        userId: parseInt(userId),
        watchedDuration: Math.floor(Math.random() * 120) + 30 // Random duration
    };
    
    try {
        await apiRequest(`/content/${contentId}/watch`, {
            method: 'POST',
            body: JSON.stringify(watchData)
        });
        
        showToast('Tomosha qilish qayd etildi!', 'success');
    } catch (error) {
        console.error('Error watching content:', error);
    }
}

function loadStatistics() {
    // Placeholder for statistics loading
    showToast('Statistika yuklanmoqda...', 'success');
}

// Pagination Functions
function updatePagination(data) {
    const pageInfo = document.getElementById('pageInfo');
    const prevButton = document.getElementById('prevPage');
    const nextButton = document.getElementById('nextPage');
    
    if (data && typeof data.currentPage === 'number') {
        pageInfo.textContent = `${data.currentPage + 1}-sahifa`;
        prevButton.disabled = data.currentPage === 0;
        nextButton.disabled = data.currentPage >= data.totalPages - 1;
    }
}

function changePage(direction) {
    const newPage = currentPage + direction;
    if (newPage >= 0) {
        currentPage = newPage;
        loadContent(currentPage);
    }
}

// Modal Functions
function showModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
        document.body.style.overflow = 'hidden';
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = 'auto';
    }
}

function showLoginModal() {
    showModal('loginModal');
}

function showRegisterModal() {
    showModal('registerModal');
}

function showAddContentModal() {
    showModal('addContentModal');
}

function showAddUserModal() {
    showModal('addUserModal');
}

function showAddActorModal() {
    showModal('addActorModal');
}

function showAddGenreModal() {
    showModal('addGenreModal');
}

function showRateModal(contentId = null) {
    if (contentId) {
        currentContentId = contentId;
    }
    showModal('rateModal');
}

function showSubscriptionModal() {
    showModal('subscriptionModal');
}

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = 'none';
        document.body.style.overflow = 'auto';
    }
}

// Toast Notification
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toastMessage');
    
    toastMessage.textContent = message;
    toast.className = `toast ${type}`;
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Loading Spinner
function showLoadingSpinner(show) {
    const spinner = document.getElementById('loadingSpinner');
    if (show) {
        spinner.classList.add('show');
    } else {
        spinner.classList.remove('show');
    }
}

// Search functionality (if needed)
function searchContent(query) {
    // Implementation for content search
    console.log('Searching for:', query);
}

// Utility Functions
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('uz-UZ');
}

function formatDuration(minutes) {
    const hours = Math.floor(minutes / 60);
    const mins = minutes % 60;
    return hours > 0 ? `${hours}s ${mins}d` : `${mins}d`;
}

// Error Handling
window.addEventListener('error', function(e) {
    console.error('JavaScript Error:', e.error);
    showToast('Dasturda xatolik yuz berdi', 'error');
});

// Handle unhandled promise rejections
window.addEventListener('unhandledrejection', function(e) {
    console.error('Unhandled Promise Rejection:', e.reason);
    showToast('Ma\'lumotlarni yuklashda xatolik', 'error');
});

// Initialize tooltips or other UI enhancements
document.addEventListener('DOMContentLoaded', function() {
    // Add smooth scrolling to navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Add loading states to buttons
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function() {
            const submitButton = form.querySelector('button[type="submit"]');
            if (submitButton) {
                submitButton.disabled = true;
                submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Yuklanmoqda...';
                
                setTimeout(() => {
                    submitButton.disabled = false;
                    submitButton.innerHTML = submitButton.getAttribute('data-original-text') || 'Yuborish';
                }, 3000);
            }
        });
    });
});

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // ESC key to close modals
    if (e.key === 'Escape') {
        const openModal = document.querySelector('.modal[style*="block"]');
        if (openModal) {
            openModal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    }
    
    // Ctrl/Cmd + K for search (if implemented)
    if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
        e.preventDefault();
        // Focus on search input if available
        const searchInput = document.querySelector('input[type="search"]');
        if (searchInput) {
            searchInput.focus();
        }
    }
});