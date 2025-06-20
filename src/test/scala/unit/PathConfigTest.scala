package unit


import config.PathConfig
import org.scalatest.funsuite.AnyFunSuite

class PathConfigTest extends AnyFunSuite {

  test("Should build correct paths based on base path") {
    val paths = PathConfig.createPaths("/data/simpleforge")

    assert(paths.output == "/data/simpleforge/output")
    assert(paths.metrics == "/data/simpleforge/metrics")
    assert(paths.badData == "/data/simpleforge/bad_data")
  }
}
