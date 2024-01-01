function sortCities() {
    var select = document.querySelector('select[name="city"]');
    var options = Array.from(select.options);
    options.sort(function(a, b) {
        return a.text.localeCompare(b.text, 'en', { sensitivity: 'accent' });
    });
    select.innerHTML = '';
    options.forEach(function(option) {
        select.add(option);
    });
}

window.onload = function() {
    sortCities();
};
