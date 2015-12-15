import math

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
    self.midPoint = Point(self.size / 2, self.size / 2)


  def createPath(self):
    
    lineLength = self.size / 2
    path = '  <path d="M{0} {1} L0 {2} L{2} 0 Z"/>'.format(self.midPoint.x, self.midPoint.y, lineLength)

    a = 0
    delta = 360 / self.segmentCount
    
    while a < 360:

      x1 = self.midPoint.x + lineLength * math.cos(math.radians(a))
      y1 = self.midPoint.y + lineLength * math.sin(math.radians(a))
      p1 = Point(x1, y1)

      path += '\n' + self.createColourPoint(p1, 'green')

      a += delta



    
    return path


  def createPoint(self, point):
    
    return self.createColourPoint(point, 'red')


  def createColourPoint(self, point, colour):
    
    return '  <circle cx="{0}" cy="{1}" r="2" fill="{2}"/>'.format(point.x, point.y, colour)


  def debugPoints(self):
    
    points = ('  <!-- debug points -->',
              self.createPoint(Point(0, 0)),
              self.createPoint(Point(0, self.size)),
              self.createPoint(Point(self.size, 0)),
              self.createPoint(Point(self.size, self.size)),
              self.createPoint(self.midPoint))
    return '\n'.join(points)


  def createSvg(self):
    
    return '\n'.join(('<?xml version="1.0" standalone="no"?>',
      '<svg width="{0}px" height="{0}px" version="1.1" xmlns="http://www.w3.org/2000/svg">'.format(self.size),
      self.createPath(),
      self.debugPoints(),
      '</svg>'))


# Run the svg generator and save the results to file
svggen = SvgGenerator()
print(svggen.createSvg())
text_file = open("Output.svg", "w")
text_file.write(svggen.createSvg())
text_file.close()