console.log("contact.js loaded");

const viewContactModel = document.getElementById('view_contact_model');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_model',
    override: true
};

const contactModel = new Modal(viewContactModel, options, instanceOptions);

function openContactModal() {
    console.log("hiii");

    contactModel.show();
}
function closeContactModal() {
    contactModel.hide();
}
async function loadContactData(id) {
    console.log("Contact ID:", id);
    try {
        const data = await (await fetch(`http://localhost:8081/api/contacts/${id}`)).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.getElementById("contact_email").innerHTML = data.email;
        document.getElementById("contact_image").src = data.picture;
        document.getElementById("contact_phone").innerHTML = data.phone;
        document.getElementById("contact_address").innerHTML = data.address;
        document.getElementById("contact_about").innerHTML = data.description;
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML =
                "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
        } else {
            contactFavorite.innerHTML = "Not Favorite Contact";
        }
        document.querySelector("#contact_website").href = data.websiteLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact_linkedIn").href = data.linkedInLink;
        document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
        openContactModal()
    } catch (error) {
        console.log(error);

    }

}


