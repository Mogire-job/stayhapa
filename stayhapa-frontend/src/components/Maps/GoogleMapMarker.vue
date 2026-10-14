<template>
  <div></div>
</template>

<script>
export default {
  name: 'GoogleMapMarker',
  props: {
    map: {
      type: Object,
      required: true
    },
    position: {
      type: Object,
      required: true
    },
    draggable: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    label: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      marker: null
    };
  },
  mounted() {
    this.createMarker();
  },
  methods: {
    createMarker() {
      this.marker = new google.maps.Marker({
        position: this.position,
        map: this.map,
        draggable: this.draggable,
        title: this.title,
        label: this.label
          ? { text: this.label, color: '#fff', fontSize: '11px', fontWeight: '700' }
          : undefined,
        animation: google.maps.Animation.DROP
      });

      // Add event listeners
      this.marker.addListener('click', () => {
        this.$emit('marker-click', this.marker);
      });

      this.marker.addListener('dragend', (event) => {
        this.$emit('drag-end', event);
      });
    }
  },
  watch: {
    position(newPosition) {
      if (this.marker) {
        this.marker.setPosition(newPosition);
      }
    },
    map(newMap) {
      if (this.marker) {
        this.marker.setMap(newMap);
      }
    }
  },
  beforeDestroy() {
    if (this.marker) {
      this.marker.setMap(null);
    }
  },
  beforeUnmount() {
    if (this.marker) {
      this.marker.setMap(null);
      this.marker = null;
    }
  }
};
</script>