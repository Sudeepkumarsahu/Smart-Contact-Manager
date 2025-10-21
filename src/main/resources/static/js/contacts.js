console.log("contact.js loaded");
const baseURL = "http://localhost:8081";
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
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
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
            contactFavori
            te.innerHTML = "Not Favorite Contact";
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
    async function deleteContact(id) {
        console.log(id);
        
        const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: "bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700",
  cancelButton: "bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
  },
  buttonsStyling: false
});
swalWithBootstrapButtons.fire({
  title: "Are you sure?",
  text: "You won't be able to revert this!",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "Yes, delete it!",
  cancelButtonText: "No, cancel!",
  reverseButtons: true
}).then((result) => {
  if (result.isConfirmed) {
    swalWithBootstrapButtons.fire({
      title: "Deleted!",
      text: "Your file has been deleted.",
      icon: "success"
    });

    const url = `${baseURL}/user/contacts/delete/`+id;
    window.location.replace(url);
    
  } else if (
    /* Read more about handling dismissals below */
    result.dismiss === Swal.DismissReason.cancel
  ) {
    swalWithBootstrapButtons.fire({
      title: "Cancelled",
      text: "Your imaginary file is safe :)",
      icon: "error"
    });
  }
});
        
    }



