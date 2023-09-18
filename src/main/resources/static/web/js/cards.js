Vue.createApp({
  data() {
    return {
      clientInfo: {},
      creditCards: [],
      debitCards: [],
      errorToats: null,
      errorMsg: null,
      id:"",
    };
  },
  methods: {
    async getData() {
      try {
        const response = await axios.get('/api/clients/current');
        this.clientInfo = response.data;
        this.creditCards = this.clientInfo.cards.filter((card) => card.type === 'CREDIT');
        this.debitCards = this.clientInfo.cards.filter((card) => card.type === 'DEBIT');
      } catch (error) {
        this.errorMsg = 'Error getting data';
        // Verificar si el objeto 'errorToats' no es nulo antes de intentar acceder a su propiedad 'show'
        if (this.errorToats?.show) {
          this.errorToats.show();
        }
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('en-gb');
    },
    async signOut() {
      try {
        const response = await axios.post('/api/logout');
        window.location.href = '/web/index.html';
      } catch (error) {
        this.errorMsg = 'Sign out failed';
        // Verificar si el objeto 'errorToats' no es nulo antes de intentar acceder a su propiedad 'show'
        if (this.errorToats?.show) {
          this.errorToats.show();
        }
      }
    },
    eliminarCard(id){
      console.log("id car eliminada es: "+ id);
      Swal.fire({
        text: "¿Desea eliminar esta tarjeta?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si!'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.patch('/api/clients/current/cards/delete', "id="+ id).then((res)=> {
            Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'Tarjeta eliminada con exito',
              showConfirmButton: false,
              timer: 1500,
            });
            console.log("hola");
            this.getData();
           // window.location.reload(); // Agregar esta línea para actualizar la página
          }).then(() => this.getData());
        }
      });
    },
  },
  mounted() {
    this.getData();
  },
}).mount('#app');

