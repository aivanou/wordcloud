all: engine

engine: install_engine test_engine

install_engine:
	cd WordCloudEngine && $(MAKE) install

test_engine: 
	cd WordCloudEngine && $(MAKE) test