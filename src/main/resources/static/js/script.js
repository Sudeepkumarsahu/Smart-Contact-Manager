console.log("script loaded successfully!");
//change theme working with localStorage and button click event
let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded', () => { // Wait until the DOM is fully loaded before changing theme
    changeTheme();// Initialize theme based on local storage
}); 

function changeTheme() {
    changePageTheme(currentTheme, currentTheme);

    const changeThemeButton = document.querySelector('#theme_change_button');

    // Set the button text initially
    changeThemeButton.querySelector("span").textContent =
        currentTheme === 'light' ? 'Dark' : 'Light';

    // Toggle theme on click
    changeThemeButton.addEventListener('click', () => {
        const oldTheme = currentTheme;
        currentTheme = currentTheme === 'light' ? 'dark' : 'light';
        changePageTheme(currentTheme, oldTheme);
    });
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

function changePageTheme(theme, oldTheme) {
    setTheme(theme);
console.log(theme);

    // Remove previous theme and add new one
    document.querySelector('html').classList.remove(oldTheme);
    document.querySelector('html').classList.add(theme);

    // Update button text
    document.querySelector("#theme_change_button span").textContent =
        theme === 'light' ? 'Dark' : 'Light';
}

//end of change theme working