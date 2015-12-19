import math, argparse

# Simple Point class
class Point:
  def __init__(self, x, y):
    self.x, self.y = x, y

  def __str__(self):
    return "{}, {}".format(self.x, self.y)

  def __neg__(self):
    return Point(-self.x, -self.y)

  def __add__(self, point):
    return Point(self.x+point.x, self.y+point.y)

  def __sub__(self, point):
    return self + -point


# Create an SVG file
class SvgGenerator:


  def __init__ (self):
    
    self.size = 100 # 100px
    self.segmentCount = 6
    self.debug = False
    self.outputFile = "Output.svg"

    parser = argparse.ArgumentParser()
    parser.add_argument("-d", "--debug",
                    help="add debug values to the output",
                    action='store_true')
    parser.add_argument("-s", "--segmentCount", type=int,
                    help="number of segments in the generated shape")
    parser.add_argument("-o", "--outputFile", type=str,
                    help="output file")
    args = parser.parse_args()

    if args.debug:
      print('debug: ', args.debug)
      self.debug = args.debug
    if args.segmentCount:
      print('segmentCount: ', args.segmentCount)
      self.segmentCount = args.segmentCount
    if args.outputFile:
      self.outputFile = args.outputFile

    self.midPoint = Point(self.size / 2, self.size / 2)

    if self.segmentCount % 2 != 0:
      print('WARN: Odd number of segments chosen; Output might be.... strange :/\n')


  def createPath(self):
    
    lineLength = self.size / 2
    a = 0
    delta = 360 / self.segmentCount

    points = []
    
    while a < 360:
      x1 = self.midPoint.x + lineLength * math.cos(math.radians(a))
      y1 = self.midPoint.y + lineLength * math.sin(math.radians(a))
      p1 = Point(x1, y1)
      points.append(p1)
      a += delta
    
    pathData = ''

    for i in range(0, len(points), 2):
      if i + 1 >= len(points):
        break

      pathData += 'M{0} {1} L{2} {3} L{4} {5} Z '.format(self.midPoint.x, self.midPoint.y,
        points[i].x, points[i].y, points[i+1].x, points[i+1].y)

    path = '  <path d="{0}"/>'.format(pathData)

    if self.debug:
      for p in points:
        path += '\n' + self.createColourPoint(p, 'green')

    return path


  def createPoint(self, point):
    
    return self.createColourPoint(point, 'red')


  def createColourPoint(self, point, colour):
    
    return '  <circle cx="{0}" cy="{1}" r="2" fill="{2}"/>'.format(point.x, point.y, colour)


  def debugPoints(self):
    
    if not self.debug:
      return ''
    points = ('  <!-- debug points -->',
              self.createPoint(Point(0, 0)),
              self.createPoint(Point(0, self.size)),
              self.createPoint(Point(self.size, 0)),
              self.createPoint(Point(self.size, self.size)),
              self.createPoint(self.midPoint))
    return '\n'.join(points)


  def createSvg(self):
    
    svg = '\n'.join(('<?xml version="1.0" standalone="no"?>',
      '<svg width="{0}px" height="{0}px" version="1.1" xmlns="http://www.w3.org/2000/svg">'.format(self.size),
      self.createPath(),
      self.debugPoints(),
      '</svg>'))
    if self.debug:
      print(svg)
    return svg


  def createSvgFile(self):

    text_file = open(self.outputFile, "w")
    text_file.write(svggen.createSvg())
    text_file.close()
    if self.debug:
      print("File created: ", self.outputFile)


# Run the svg generator and save the results to file
svggen = SvgGenerator()
svggen.createSvgFile()
