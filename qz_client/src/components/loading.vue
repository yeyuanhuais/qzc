<template>
	<div class="container">
		<div id="loader-wrapper">
			<div id="loader-wrapper-internal">
				<div id="loader1" class="fa fa-cog loader"></div>
				<div id="loader3" class="fa fa-cog loader"></div>
			</div>
			<div id="loader2" class="fa fa-cog loader"></div>
		</div>
	</div>
</template>

<script>
export default {}
</script>

<style scoped>
.container {
	width: 100vw;
	height: 100vh;
	background: rgba(255, 255, 255, 0.62);
  display: flex;
  justify-content: center;
  align-items: center;
}
#loader-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
#loader-wrapper-internal {
  display: flex;
  flex-direction: column;
  transform: translateX(30%);
}
#loader1 {
	color: #3a4652;
	font-size: 8rem;
	text-align: center;
	width: 8rem;
	height: 8rem;
	animation: animate-1 1s infinite linear;
}
#loader2 {
	color: #d72f2b;
	font-size: 15rem;
	text-align: center;
	height: 15rem;
	width: 15rem;
	animation: animate-2 1s infinite linear;
}
#loader3 {
	color: #3a4652;
	font-size: 8rem;
	text-align: center;
	width: 8rem;
	height: 8rem;
	animation: animate-3 1s infinite linear;
}
@keyframes animate-1 {
	100% {
		transform: rotate(-180deg);
	}
}
@keyframes animate-2 {
	100% {
		transform: rotate(180deg);
	}
}
@keyframes animate-3 {
	100% {
		transform: rotate(-180deg);
	}
}
</style>