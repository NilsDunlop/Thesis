import clang.cindex

class StaticMethodVisitor:
    def __init__(self):
        self.static_methods = []

    def visit_cxx_method_decl(self, node):
        # Check if the method is static
        if node.is_static_method():
            # Extract the name and return type of the method
            name = node.spelling
            return_type = node.result_type.spelling

            # Extract the parameters of the method
            parameters = []
            for param in node.get_arguments():
                parameters.append(f'{param.type.spelling} {param.spelling}')

            # Add the static method to the list
            self.static_methods.append((name, return_type, parameters))

def extract_static_methods(source_file):
    # Set up the Clang index
    index = clang.cindex.Index.create()

    # Parse the source code and create an AST
    translation_unit = index.parse(source_file)

    # Traverse the AST with the visitor
    visitor = StaticMethodVisitor()
    for node in translation_unit.cursor.walk_preorder():
        if node.kind == clang.cindex.CursorKind.CXX_METHOD:
            visitor.visit_cxx_method_decl(node)

    count = len(visitor.static_methods)

    # Return the list of static methods
    return visitor.static_methods, count
